package cn.easy.boot3.admin.loginLog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.loginLog.entity.LoginLog;
import cn.easy.boot3.admin.loginLog.entity.LoginLogQuery;
import cn.easy.boot3.admin.loginLog.service.ILoginLogService;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author zoe
 * @date 2023/08/02
 * @description 登录日志 前端控制器
 */
@Slf4j
@Tag(name = "登录日志接口")
@RestController
@RequestMapping("/admin/loginLog")
public class LoginLogController extends BaseController {

    @Resource
    private ILoginLogService loginLogService;


    @SaCheckPermission(value = "log:login:log:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取登录日志列表")
    @EasyLog(module = "分页获取登录日志列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<LoginLog>> page(@Validated LoginLogQuery query) {
        IPage<LoginLog> page = loginLogService.selectPage(query);
        return Result.success(page);
    }

    @SaCheckPermission(value = "log:login:log:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除登录日志")
    @EasyLog(module = "删除登录日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(loginLogService.deleteById(id));
    }

    @SaCheckPermission(value = "log:login:log:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除登录日志")
    @EasyLog(module = "批量删除登录日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(loginLogService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "log:login:log:clear")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "清空登录日志")
    @EasyLog(module = "清空登录日志", operateType = OperateTypeEnum.CLEAR)
    @PostMapping("/clear")
    public Result clear() {
        return Result.success(loginLogService.clear());
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "log:login:log:export")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导出登录日志")
    @EasyLog(module = "导出登录日志", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody LoginLogQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), LoginLog.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("登录日志信息列表").build();
        while (true) {
            IPage<LoginLog> page = loginLogService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }

}
