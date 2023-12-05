package cn.easy.boot3.admin.scheduledTask.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.scheduledTask.entity.*;
import cn.easy.boot3.admin.scheduledTask.service.IScheduledTaskService;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.admin.scheduledTask.entity.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zoe
 * @date 2023/08/04
 * @description 定时任务 前端控制器
 */
@Tag(name = "定时任务接口")
@RestController
@RequestMapping("/admin/scheduledTask")
public class ScheduledTaskController extends BaseController {

    @Resource
    private IScheduledTaskService scheduledTaskService;

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "ops:scheduled:task:start:now")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "立即执行定时任务")
    @EasyLog(module = "立即执行定时任务", operateType = OperateTypeEnum.START_JOB)
    @PostMapping("/startNow")
    public Result startNow(@Validated @RequestBody StartNowJobDTO dto) {
        scheduledTaskService.startNow(dto);
        return Result.success();
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "ops:scheduled:task:change")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "恢复/暂停定时任务")
    @EasyLog(module = "恢复/暂停定时任务", operateType = OperateTypeEnum.RESUME_OR_PAUSE_JOB)
    @PostMapping("/change")
    public Result change(@Validated @RequestBody ChangeJobDTO dto) {
        scheduledTaskService.change(dto);
        return Result.success();
    }

    @SaCheckPermission(value = "ops:scheduled:task:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取定时任务列表")
    @EasyLog(module = "分页获取定时任务列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<ScheduledTask>> page(@Validated ScheduledTaskQuery query) {
        return Result.success(scheduledTaskService.selectPage(query));
    }

    @SaCheckPermission(value = "ops:scheduled:task:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取定时任务详情")
    @EasyLog(module = "获取定时任务详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<ScheduledTask> detail(@PathVariable Long id) {
        return Result.success(scheduledTaskService.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "ops:scheduled:task:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建定时任务")
    @EasyLog(module = "创建定时任务", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody ScheduledTaskCreateDTO dto) {
        return Result.r(scheduledTaskService.create(dto));
    }

    @SaCheckPermission(value = "ops:scheduled:task:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑定时任务")
    @EasyLog(module = "编辑定时任务", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody ScheduledTaskUpdateDTO dto) {
        return Result.r(scheduledTaskService.updateById(dto));
    }

    @SaCheckPermission(value = "ops:scheduled:task:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除定时任务")
    @EasyLog(module = "删除定时任务", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(scheduledTaskService.deleteById(id));
    }

    @SaCheckPermission(value = "ops:scheduled:task:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除定时任务")
    @EasyLog(module = "批量删除定时任务", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(scheduledTaskService.deleteBatchByIds(ids));
    }

}
