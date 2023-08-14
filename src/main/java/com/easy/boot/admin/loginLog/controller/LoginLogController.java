package com.easy.boot.admin.loginLog.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.loginLog.entity.LoginLog;
import com.easy.boot.admin.loginLog.entity.LoginLogQuery;
import com.easy.boot.admin.loginLog.service.ILoginLogService;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.properties.EasyFile;
import com.easy.boot.exception.FileException;
import com.easy.boot.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Resource
    private EasyFile easyFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    private Integer maxLimit;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取登录日志列表")
    @EasyLog(module = "获取登录日志列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<LoginLog>> page(@Validated LoginLogQuery query) {
        return Result.success(loginLogService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取登录日志详情")
    @EasyLog(module = "获取登录日志详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<LoginLog> detail(@PathVariable Long id) {
        return Result.success(loginLogService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除登录日志")
    @EasyLog(module = "删除登录日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(loginLogService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除登录日志")
    @EasyLog(module = "批量删除登录日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(loginLogService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "清空操作日志")
    @EasyLog(module = "清空操作日志", operateType = OperateTypeEnum.CLEAR)
    @PostMapping("/clear")
    public Result clear() {
        return Result.r(loginLogService.clear());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出登录日志")
    @EasyLog(module = "导出登录日志", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody LoginLogQuery query) {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "登录日志");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, LoginLog.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("登录日志").build();
        while (true) {
            IPage<LoginLog> page = loginLogService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("导出Excel失败 e -> ", e);
            throw new FileException("导出Excel失败");
        }
    }
}
