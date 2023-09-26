package com.easy.boot.admin.scheduledTask.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.scheduledTask.entity.*;
import com.easy.boot.admin.scheduledTask.service.IScheduledTaskService;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
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
    @EasyLog(module = "立即执行定时任务", operateType = OperateTypeEnum.START_JOB)
    @PostMapping("/startNow")
    public Result startNow(@Validated StartNowJobDTO dto) {
        scheduledTaskService.startNow(dto);
        return Result.success();
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "恢复/暂停定时任务")
    @EasyLog(module = "恢复/暂停定时任务", operateType = OperateTypeEnum.RESUME_OR_PAUSE_JOB)
    @PostMapping("/change")
    public Result change(@Validated ChangeJobDTO dto) {
        scheduledTaskService.change(dto);
        return Result.success();
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取定时任务列表")
    @EasyLog(module = "分页获取定时任务列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<ScheduledTask>> page(@Validated ScheduledTaskQuery query) {
        return Result.success(scheduledTaskService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取定时任务详情")
    @EasyLog(module = "获取定时任务详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<ScheduledTask> detail(@PathVariable Long id) {
        return Result.success(scheduledTaskService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建定时任务")
    @EasyLog(module = "创建定时任务", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody ScheduledTaskCreateDTO dto) {
        return Result.r(scheduledTaskService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑定时任务")
    @EasyLog(module = "编辑定时任务", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody ScheduledTaskUpdateDTO dto) {
        return Result.r(scheduledTaskService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除定时任务")
    @EasyLog(module = "删除定时任务", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(scheduledTaskService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除定时任务")
    @EasyLog(module = "批量删除定时任务", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(scheduledTaskService.deleteBatchByIds(ids));
    }

}
