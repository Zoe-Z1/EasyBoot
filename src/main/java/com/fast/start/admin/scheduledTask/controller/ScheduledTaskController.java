package com.fast.start.admin.scheduledTask.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.admin.scheduledTask.entity.*;
import com.fast.start.admin.scheduledTask.service.IScheduledTaskService;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.log.FastLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zoe
 * @date 2023/08/04
 * @description 定时任务 前端控制器
 */
@Api(tags = "定时任务接口")
@RestController
@RequestMapping("/admin/scheduledTask")
public class ScheduledTaskController extends BaseController {

    @Resource
    private IScheduledTaskService scheduledTaskService;

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "立即执行定时任务")
    @FastLog(module = "立即执行定时任务", operateType = OperateTypeEnum.START_JOB)
    @PostMapping("/startNow")
    public Result startNow(@Validated StartNowJobDTO dto) {
        scheduledTaskService.startNow(dto);
        return Result.success();
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "恢复/暂停定时任务")
    @FastLog(module = "恢复/暂停定时任务", operateType = OperateTypeEnum.RESUME_OR_PAUSE_JOB)
    @PostMapping("/change")
    public Result change(@Validated ChangeJobDTO dto) {
        scheduledTaskService.change(dto);
        return Result.success();
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取定时任务列表")
    @FastLog(module = "获取定时任务列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<ScheduledTask>> page(@Validated ScheduledTaskQuery query) {
        return Result.success(scheduledTaskService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取定时任务详情")
    @FastLog(module = "获取定时任务详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<ScheduledTask> detail(@PathVariable Long id) {
        return Result.success(scheduledTaskService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建定时任务")
    @FastLog(module = "创建定时任务", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody ScheduledTaskCreateDTO dto) {
        return Result.r(scheduledTaskService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑定时任务")
    @FastLog(module = "编辑定时任务", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody ScheduledTaskUpdateDTO dto) {
        return Result.r(scheduledTaskService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除定时任务")
    @FastLog(module = "删除定时任务", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(scheduledTaskService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除定时任务")
    @FastLog(module = "批量删除定时任务", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(scheduledTaskService.deleteBatchByIds(ids));
    }

}
