package com.easy.boot.admin.generate.controller;

import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.admin.generate.service.GenerateService;
import com.easy.boot.admin.generateColumn.service.IGenerateColumnService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.Page;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@Slf4j
@Api(tags = "代码生成接口")
@Validated
@RestController
@RequestMapping("/admin/generate")
public class GenerateController {

    @Resource
    private GenerateService generateService;

    @Resource
    private IGenerateColumnService generateColumnService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成Table列表")
    @EasyLog(module = "获取代码生成Table列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/page")
    public Result<Page<DatabaseTable>> page(@Validated GenerateTableQuery query) {
        return Result.success(generateService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "生成代码")
    @EasyLog(module = "生成代码", operateType = OperateTypeEnum.GENERATE)
    @PostMapping(value = "/code/{tableName}")
    public Result generateCode(@PathVariable String tableName) {
        generateColumnService.generateCode(tableName);
        return Result.success();
    }

}
