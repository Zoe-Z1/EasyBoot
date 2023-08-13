package com.fast.start.admin.operationLog.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.operationLog.entity.OperationLog;
import com.fast.start.admin.operationLog.entity.OperationLogQuery;
import com.fast.start.admin.operationLog.entity.OperationLogUpdateDTO;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.admin.operationLog.service.IOperationLogService;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.excel.UploadDTO;
import com.fast.start.common.excel.ImportVO;
import com.fast.start.common.log.FastLog;
import com.fast.start.common.properties.FastFile;
import com.fast.start.exception.FileException;
import com.fast.start.utils.FileUtil;
import com.fast.start.utils.JsonUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 操作日志 前端控制器
 */
@Slf4j
@Api(tags = "操作日志接口")
@RestController
@RequestMapping("/admin/operationLog")
public class OperationLogController extends BaseController {

    @Resource
    private IOperationLogService operationLogService;

    @Resource
    private FastFile fastFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    private Integer maxLimit;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取操作日志列表")
    @FastLog(module = "获取操作日志列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<OperationLog>> page(@Validated OperationLogQuery query) {
        return Result.success(operationLogService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取操作日志详情")
    @FastLog(module = "获取操作日志详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<OperationLog> detail(@PathVariable Long id) {
        return Result.success(operationLogService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除操作日志")
    @FastLog(module = "删除操作日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(operationLogService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除操作日志")
    @FastLog(module = "批量删除操作日志", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(operationLogService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "清空操作日志")
    @FastLog(module = "清空操作日志", operateType = OperateTypeEnum.CLEAR)
    @PostMapping("/clear")
    public Result clear() {
        return Result.r(operationLogService.clear());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入操作日志 - 用于测试")
    @FastLog(module = "导入操作日志 - 用于测试", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) {
        Assert.notNull(dto.getFile(), "文件不能为空");
        try {
            List<OperationLog> list = EasyExcel.read(dto.getFile().getInputStream())
                    .head(OperationLog.class)
                    .excelType(FileUtil.getExcelType(dto.getFile()))
                    .sheet()
                    .doReadSync();
            long start = DateUtil.current();
            List<OperationLogUpdateDTO> updateDTOS = JsonUtil.copyList(list, OperationLogUpdateDTO.class);
            long end = DateUtil.current();
            long time = end - start;
//            operationLogService.saveBatch(list);
            System.out.println("DateUtil.start() = " + start);
            System.out.println("DateUtil.end() = " + end);
            System.out.println("DateUtil.time() = " + time);

            return Result.success();
        } catch (IOException e) {
            log.error("导入Excel失败 e -> ", e);
            throw new FileException("导入Excel失败");
        }
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出操作日志")
    @FastLog(module = "导出操作日志", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody OperationLogQuery query) {
        String filePath = FileUtil.getFullPath(fastFile.getExcelPath(), "操作日志");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, OperationLog.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("操作日志").build();
        while (true) {
            IPage<OperationLog> page = operationLogService.selectPage(query);
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
