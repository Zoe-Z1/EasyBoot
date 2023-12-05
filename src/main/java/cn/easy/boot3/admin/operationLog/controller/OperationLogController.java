package cn.easy.boot3.admin.operationLog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.operationLog.entity.OperationLogQuery;
import cn.easy.boot3.admin.operationLog.service.IOperationLogService;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.operationLog.entity.OperationLog;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
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
 * @date 2023/07/30
 * @description 操作日志 前端控制器
 */
@Slf4j
@Tag(name = "操作日志接口")
@RestController
@RequestMapping("/admin/operationLog")
public class OperationLogController extends BaseController {

    @Resource
    private IOperationLogService operationLogService;



    @SaCheckPermission(value = "log:operation:log:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取操作日志列表")
    @EasyLog(module = "分页获取操作日志列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<OperationLog>> page(@Validated OperationLogQuery query) {
        return Result.success(operationLogService.selectPage(query));
    }

    @SaCheckPermission(value = "log:operation:log:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取操作日志详情")
    @EasyLog(module = "获取操作日志详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<OperationLog> detail(@PathVariable Long id) {
        return Result.success(operationLogService.detail(id));
    }

    @SaCheckPermission(value = "log:operation:log:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除操作日志")
    @EasyLog(module = "删除操作日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(operationLogService.deleteById(id));
    }

    @SaCheckPermission(value = "log:operation:log:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除操作日志")
    @EasyLog(module = "批量删除操作日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(operationLogService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "log:operation:log:clear")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "清空操作日志")
    @EasyLog(module = "清空操作日志", operateType = OperateTypeEnum.CLEAR)
    @PostMapping("/clear")
    public Result clear() {
        return Result.success(operationLogService.clear());
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "log:operation:log:export")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导出操作日志")
    @EasyLog(module = "导出操作日志", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody OperationLogQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), OperationLog.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("操作日志信息列表").build();
        while (true) {
            IPage<OperationLog> page = operationLogService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }

}
