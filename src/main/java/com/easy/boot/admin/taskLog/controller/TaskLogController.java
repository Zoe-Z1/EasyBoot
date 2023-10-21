package com.easy.boot.admin.taskLog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.taskLog.entity.TaskLog;
import com.easy.boot.admin.taskLog.entity.TaskLogQuery;
import com.easy.boot.admin.taskLog.service.ITaskLogService;
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
 * @date 2023/08/06
 * @description 调度日志 前端控制器
 */
@Slf4j
@Api(tags = "调度日志接口")
@RestController
@RequestMapping("/admin/taskLog")
public class TaskLogController extends BaseController {

    @Resource
    private ITaskLogService taskLogService;


    @SaCheckPermission(value = "log:task:log:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取调度日志列表")
    @EasyLog(module = "分页获取调度日志列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<TaskLog>> page(@Validated TaskLogQuery query) {
        return Result.success(taskLogService.selectPage(query));
    }

    @SaCheckPermission(value = "log:task:log:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除调度日志")
    @EasyLog(module = "删除调度日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(taskLogService.deleteById(id));
    }

    @SaCheckPermission(value = "log:task:log:batch:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除调度日志")
    @EasyLog(module = "批量删除调度日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(taskLogService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "log:task:log:clear")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "清空调度日志")
    @EasyLog(module = "清空调度日志", operateType = OperateTypeEnum.CLEAR)
    @PostMapping("/clear")
    public Result clear() {
        return Result.success(taskLogService.clear());
    }

    @SaCheckPermission(value = "log:task:log:export")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出调度日志")
    @EasyLog(module = "导出调度日志", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody TaskLogQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), TaskLog.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(TaskLog.class))
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("调度日志信息列表").build();
        while (true) {
            IPage<TaskLog> page = taskLogService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }
}
