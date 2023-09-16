package com.easy.boot.admin.generateColumn.controller;

import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnUpdateDTO;
import com.easy.boot.admin.generateColumn.service.IGenerateColumnService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置接口
 */
@Slf4j
@Api(tags = "代码生成列配置接口")
@RestController
@RequestMapping("/admin/generateColumn")
public class GenerateColumnController extends BaseController {

    @Resource
    private IGenerateColumnService generateColumnService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成Table中的列")
    @EasyLog(module = "获取代码生成Table中的列", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/list")
    public Result<List<GenerateColumn>> list(@Validated GenerateColumnQuery query) {
        return Result.success(generateColumnService.selectList(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑代码生成列配置")
    @EasyLog(module = "编辑代码生成列配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody List<GenerateColumnUpdateDTO> dto) {
        return Result.r(generateColumnService.updateBatchById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "重置代码生成列配置")
    @EasyLog(module = "重置代码生成列配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{tableName}")
    public Result delete(@PathVariable String tableName) {
        return Result.r(generateColumnService.deleteByTableName(tableName));
    }

}
