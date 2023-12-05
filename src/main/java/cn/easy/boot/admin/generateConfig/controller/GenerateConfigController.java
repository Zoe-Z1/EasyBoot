package cn.easy.boot.admin.generateConfig.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot.admin.generateConfig.entity.GenerateConfigGlobalUpdateDTO;
import cn.easy.boot.admin.generateConfig.entity.GenerateConfigQuery;
import cn.easy.boot.admin.generateConfig.entity.GenerateConfigUpdateDTO;
import cn.easy.boot.admin.generateConfig.entity.GenerateConfigVO;
import cn.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot.common.base.BaseController;
import cn.easy.boot.common.base.Result;
import cn.easy.boot.common.log.EasyLog;
import cn.easy.boot.admin.generateConfig.service.IGenerateConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


    @SaCheckPermission(value = "dev:gen:config:global:config")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成全局参数配置")
    @EasyLog(module = "获取代码生成全局参数配置", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/globalConfig")
    public Result<GenerateConfigVO> globalConfig() {
        return Result.success(generateConfigService.getGlobalConfig());
    }

    @SaCheckPermission(value = "dev:gen:config:table:config")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取代码生成Table参数配置")
    @EasyLog(module = "获取代码生成Table参数配置", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/tableConfig")
    public Result<GenerateConfigVO> tableConfig(@Validated GenerateConfigQuery query) {
        return Result.success(generateConfigService.getTableConfig(query));
    }

    @SaCheckPermission(value = "dev:gen:config:update:global")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑代码生成全局参数配置")
    @EasyLog(module = "编辑代码生成全局参数配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/updateGlobal")
    public Result updateGlobal(@Validated @RequestBody GenerateConfigGlobalUpdateDTO dto) {
        generateConfigService.updateGlobalConfig(dto);
        return Result.success();
    }

    @SaCheckPermission(value = "dev:gen:config:update:table")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑代码生成Table参数配置")
    @EasyLog(module = "编辑代码生成Table参数配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/updateTable")
    public Result updateTable(@Validated @RequestBody GenerateConfigUpdateDTO dto) {
        generateConfigService.updateByTableName(dto);
        return Result.success();
    }

    @SaCheckPermission(value = "dev:gen:config:del:global")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "重置代码生成全局参数配置")
    @EasyLog(module = "重置代码生成全局参数配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/deleteGlobal")
    public Result deleteGlobal() {
        generateConfigService.deleteGlobal();
        return Result.success();
    }

    @SaCheckPermission(value = "dev:gen:config:del:table")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "重置代码生成Table参数配置")
    @EasyLog(module = "重置代码生成Table参数配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{tableName}")
    public Result delete(@PathVariable String tableName) {
        generateConfigService.deleteByTableName(tableName);
        return Result.success();
    }

}
