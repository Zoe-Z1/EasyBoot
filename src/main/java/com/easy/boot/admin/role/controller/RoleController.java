package com.easy.boot.admin.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.role.entity.*;
import com.easy.boot.admin.role.service.IRoleService;
import com.easy.boot.admin.userRole.service.IUserRoleService;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.excel.entity.ImportVO;
import com.easy.boot.common.excel.entity.UploadDTO;
import com.easy.boot.common.excel.handler.ExportExcelErrorCellWriteHandler;
import com.easy.boot.common.excel.handler.ExportExcelSelectCellWriteHandler;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
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
 * @date 2023/07/30
 * @description 角色 前端控制器
 */
@Slf4j
@Tag(name = "角色接口")
@RestController
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserRoleService userRoleService;


    @SaCheckPermission(value = "system:role:all")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取全部角色")
    @EasyLog(module = "获取全部角色", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/all")
    public Result<List<Role>> all() {
        return Result.success(roleService.selectAll());
    }

    @SaCheckPermission(value = "system:role:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取角色列表")
    @EasyLog(module = "分页获取角色列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Role>> page(@Validated RoleQuery query) {
        return Result.success(roleService.selectPage(query));
    }

    @SaCheckPermission(value = "system:role:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取角色详情")
    @EasyLog(module = "获取角色详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<RoleVO> detail(@PathVariable Long id) {
        return Result.success(roleService.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:role:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建角色")
    @EasyLog(module = "创建角色", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody RoleCreateDTO dto) {
        return Result.r(roleService.create(dto));
    }

    @SaCheckPermission(value = "system:role:allot:user")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "角色分配用户")
    @EasyLog(module = "角色分配用户", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/allotUser")
    public Result roleAllotUser(@Validated @RequestBody RoleAllotUserDTO dto) {
        return Result.r(userRoleService.roleAllotUser(dto.getUserIds(), dto.getRoleId()));
    }

    @SaCheckPermission(value = "system:role:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑角色")
    @EasyLog(module = "编辑角色", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody RoleUpdateDTO dto) {
        return Result.r(roleService.updateById(dto));
    }

    @SaCheckPermission(value = "system:role:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除角色")
    @EasyLog(module = "删除角色", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(roleService.deleteById(id));
    }

    @SaCheckPermission(value = "system:role:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除角色")
    @EasyLog(module = "批量删除角色", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(roleService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "system:role:import")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导入角色")
    @EasyLog(module = "导入角色", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) throws IOException {
        Assert.notNull(dto.getFile(), "文件不能为空");
        List<Role> list = EasyExcel.read(dto.getFile().getInputStream())
                .head(Role.class)
                .sheet()
                .doReadSync();
        List<ImportExcelError> errors = new ArrayList<>();
        List<Role> errorList = new ArrayList<>();
        // 导入Excel处理
        roleService.importExcel(list, errorList, errors);
        String base64 = "";
        if (!errorList.isEmpty()) {
            // 将错误数据写到Excel文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out).head(Role.class)
                    .sheet("角色导入错误信息列表")
                    .registerWriteHandler(new ExportExcelSelectCellWriteHandler(Role.class))
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
    @SaCheckPermission(value = "system:role:export")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导出角色")
    @EasyLog(module = "导出角色", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody RoleQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), Role.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("角色信息列表").build();
        while (true) {
            IPage<Role> page = roleService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:role:download")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "下载角色导入模板")
    @EasyLog(module = "下载角色导入模板", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), Role.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(Role.class))
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("角色导入模板")
                .doWrite(new ArrayList<>());
    }
}
