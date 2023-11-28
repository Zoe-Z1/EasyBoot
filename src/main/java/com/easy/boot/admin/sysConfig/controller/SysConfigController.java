package com.easy.boot.admin.sysConfig.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.sysConfig.entity.*;
import com.easy.boot.admin.sysConfig.service.ISysConfigService;
import com.easy.boot.admin.sysConfigDomain.service.ISysConfigDomainService;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 系统配置 前端控制器
 */
@Slf4j
@Tag(name = "系统配置接口")
@Validated
@RestController
@RequestMapping("/admin/sysConfig")
public class SysConfigController extends BaseController {

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysConfigDomainService sysConfigDomainService;


    @SaCheckPermission(value = "system:sys:config:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取系统配置列表")
    @EasyLog(module = "获取系统配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<SysConfig>> page(@Validated SysConfigQuery query) {
        return Result.success(sysConfigService.selectPage(query));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:sys:config:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建系统配置")
    @EasyLog(module = "创建系统配置", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody SysConfigCreateDTO dto) {
        return Result.r(sysConfigService.create(dto));
    }

    @SaCheckPermission(value = "system:sys:config:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑系统配置")
    @EasyLog(module = "编辑系统配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysConfigUpdateDTO dto) {
        return Result.r(sysConfigService.updateById(dto));
    }

    @SaCheckPermission(value = "system:sys:config:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除系统配置")
    @EasyLog(module = "删除系统配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(sysConfigService.deleteById(id));
    }

    @SaCheckPermission(value = "system:sys:config:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除系统配置")
    @EasyLog(module = "批量删除系统配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(sysConfigService.deleteBatchByIds(ids));
    }


    @SaCheckPermission(value = "system:sys:config:template:list")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取系统模板配置列表")
    @EasyLog(module = "获取系统模板配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/template/list")
    public Result<List<SysTemplateConfigVO>> list(@NotNull(message = "系统配置域ID不能为空") Long domainId) {
        return Result.success(sysConfigDomainService.selectTemplateList(domainId));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:sys:config:template:save")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "保存系统模板配置")
    @EasyLog(module = "保存系统模板配置", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/template/save")
    public Result save(@Validated @RequestBody List<SysConfigCreateDTO> dtos) {
        return Result.r(sysConfigService.templateBatchSave(dtos));
    }

    @SaCheckPermission(value = "system:sys:config:template:reset")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "重置系统模板配置")
    @EasyLog(module = "重置系统模板配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/template/reset/{domainId}")
    public Result reset(@PathVariable Long domainId) {
        sysConfigService.deleteByDomainId(domainId);
        return Result.success();
    }

}
