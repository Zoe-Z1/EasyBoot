package com.easy.boot.admin.loginLog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.loginLog.entity.LoginLog;
import com.easy.boot.admin.loginLog.entity.LoginLogQuery;
import com.easy.boot.admin.loginLog.service.ILoginLogService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.handler.ExportExcelSelectCellWriteHandler;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author zoe
 * @date 2023/08/02
 * @description 登录日志 前端控制器
 */
@Slf4j
@Api(tags = "登录日志接口")
@RestController
@RequestMapping("/admin/loginLog")
public class LoginLogController extends BaseController {

    @Resource
    private ILoginLogService loginLogService;


    @SaCheckPermission(value = "system:login:log:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取登录日志列表")
    @EasyLog(module = "分页获取登录日志列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<LoginLog>> page(@Validated LoginLogQuery query) {
        return Result.success(loginLogService.selectPage(query));
    }

    @SaCheckPermission(value = "system:login:log:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除登录日志")
    @EasyLog(module = "删除登录日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(loginLogService.deleteById(id));
    }

    @SaCheckPermission(value = "system:login:log:batch:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除登录日志")
    @EasyLog(module = "批量删除登录日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(loginLogService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "system:login:log:clear")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "清空登录日志")
    @EasyLog(module = "清空登录日志", operateType = OperateTypeEnum.CLEAR)
    @PostMapping("/clear")
    public Result clear() {
        return Result.r(loginLogService.clear());
    }

    @SaCheckPermission(value = "system:login:log:export")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出登录日志")
    @EasyLog(module = "导出登录日志", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody LoginLogQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), LoginLog.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(LoginLog.class))
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
