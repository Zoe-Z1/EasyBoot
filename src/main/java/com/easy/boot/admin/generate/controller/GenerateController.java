package com.easy.boot.admin.generate.controller;

import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateTableColumn;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.admin.generate.service.GenerateService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.Page;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成Table列表")
    @EasyLog(module = "获取代码生成Table列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/page")
    public Result<Page<DatabaseTable>> page(@Validated GenerateTableQuery query) {
        return Result.success(generateService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成Table中的列")
    @EasyLog(module = "获取代码生成Table中的列", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/column/{tableName}")
    public Result<List<GenerateTableColumn>> column(@PathVariable String tableName) {
        return Result.success(generateService.selectTableColumnList(tableName));
    }

}
