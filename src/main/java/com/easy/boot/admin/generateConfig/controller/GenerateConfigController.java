package com.easy.boot.admin.generateConfig.controller;

import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigUpdateDTO;
import com.easy.boot.admin.generateConfig.entity.TableConfigQuery;
import com.easy.boot.admin.generateConfig.service.IGenerateConfigService;
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
 * @date 2023/09/10
 * @description 代码生成参数配置接口
 */
@Slf4j
@Api(tags = "代码生成参数配置接口")
@RestController
@RequestMapping("/admin/generateConfig")
public class GenerateConfigController extends BaseController {

    @Resource
    private IGenerateConfigService generateConfigService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成全局参数配置")
    @EasyLog(module = "获取代码生成全局参数配置", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/globalConfig")
    public Result<GenerateConfig> globalConfig() {
        return Result.success(generateConfigService.getGlobalConfig());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成Table参数配置")
    @EasyLog(module = "获取代码生成Table参数配置", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/tableConfig")
    public Result<GenerateConfig> tableConfig(@Validated TableConfigQuery query) {
        return Result.success(generateConfigService.getTableConfig(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑代码生成Table参数配置")
    @EasyLog(module = "编辑代码生成Table参数配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody GenerateConfigUpdateDTO dto) {
        return Result.r(generateConfigService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "重置代码生成Table参数配置")
    @EasyLog(module = "重置代码生成Table参数配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(generateConfigService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量重置代码生成Table参数配置")
    @EasyLog(module = "批量重置代码生成Table参数配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(generateConfigService.deleteBatchByIds(ids));
    }

}
