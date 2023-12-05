package cn.easy.boot3.admin.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.admin.user.entity.*;
import cn.easy.boot3.admin.user.service.AdminUserService;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.excel.entity.ImportExcelError;
import cn.easy.boot3.common.excel.entity.ImportVO;
import cn.easy.boot3.common.excel.entity.UploadDTO;
import cn.easy.boot3.common.excel.handler.ExportExcelErrorCellWriteHandler;
import cn.easy.boot3.common.excel.handler.ExportExcelSelectCellWriteHandler;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.user.entity.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Slf4j
@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/user")
public class AdminUserController extends BaseController {

    @Resource
    private AdminUserService adminUserService;

    @SaCheckPermission(value = "system:admin:user:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取用户列表")
    @EasyLog(module = "分页获取用户列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<AdminUser>> page(@Validated AdminUserQuery query) {
        return Result.success(adminUserService.selectPage(query));
    }

    @SaCheckPermission(value = "system:admin:user:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取用户详情")
    @EasyLog(module = "获取用户详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<AdminUserVO> detail(@PathVariable Long id) {
        return Result.success(adminUserService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取当前用户信息")
    @EasyLog(module = "获取当前用户信息", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/info")
    public Result<AdminUserInfo> info() {
        return Result.success(adminUserService.getInfo());
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:admin:user:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建用户")
    @EasyLog(module = "创建用户", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody AdminUserCreateDTO dto) {
        return Result.r(adminUserService.create(dto));
    }

    @SaCheckPermission(value = "system:admin:user:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑用户")
    @EasyLog(module = "编辑用户", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody AdminUserUpdateDTO dto) {
        return Result.r(adminUserService.updateById(dto));
    }

    @SaCheckPermission(value = "system:admin:user:edit:password")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "修改密码")
    @EasyLog(module = "修改密码", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/editPassword")
    public Result updatePassword(@Validated @RequestBody EditPasswordDTO dto) {
        return Result.r(adminUserService.editPassword(dto));
    }

    @SaCheckPermission(value = "system:admin:user:reset:password")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "重置密码")
    @EasyLog(module = "重置密码", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/resetPassword")
    public Result resetPassword(@Validated @RequestBody ResetPasswordDTO dto) {
        return Result.r(adminUserService.resetPassword(dto));
    }

    @SaCheckPermission(value = "system:admin:user:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除用户")
    @EasyLog(module = "删除用户", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(adminUserService.deleteById(id));
    }

    @SaCheckPermission(value = "system:admin:user:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除用户")
    @EasyLog(module = "批量删除用户", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(adminUserService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "system:admin:user:import")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导入用户")
    @EasyLog(module = "导入用户", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) throws IOException {
        Assert.notNull(dto.getFile(), "文件不能为空");
        List<AdminUser> list = EasyExcel.read(dto.getFile().getInputStream())
                .head(AdminUser.class)
                .sheet()
                .doReadSync();
        List<ImportExcelError> errors = new ArrayList<>();
        List<AdminUser> errorList = new ArrayList<>();
        // 导入Excel处理
        adminUserService.importExcel(list, errorList, errors);
        String base64 = "";
        if (!errorList.isEmpty()) {
            // 将错误数据写到Excel文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out).head(AdminUser.class)
                    .sheet("用户导入错误信息列表")
                    .registerWriteHandler(new ExportExcelSelectCellWriteHandler(AdminUser.class))
                    .registerWriteHandler(new ExportExcelErrorCellWriteHandler(errors))
                    .doWrite(errorList);
            base64 = Base64.getEncoder().encodeToString(out.toByteArray());
        }
        ImportVO importVO = ImportVO.builder()
                .count(list.size())
                .errorCount(errorList.size())
                .errorBase64(base64)
                .build();
        return Result.success(importVO);
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:admin:user:export")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导出用户")
    @EasyLog(module = "导出用户", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody AdminUserQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter writer = EasyExcel.write(response.getOutputStream(), AdminUser.class)
                .excludeColumnFieldNames(Collections.singletonList("password"))
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("用户信息列表").build();
        while (true) {
            IPage<AdminUser> page = adminUserService.selectPage(query);
            writer.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        writer.finish();
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:admin:user:download")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "下载用户导入模板")
    @EasyLog(module = "下载用户导入模板", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), AdminUser.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(AdminUser.class))
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("用户导入模板")
                .doWrite(new ArrayList<>());
    }
}
