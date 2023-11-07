package com.easy.boot.admin.sysConfig.controller;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 系统配置 前端控制器
 */
@Slf4j
@Api(tags = "系统配置接口")
@RestController
@RequestMapping("/admin/sysConfig")
public class SysConfigController extends BaseController {

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysConfigDomainService sysConfigDomainService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取系统配置列表")
    @EasyLog(module = "获取系统配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<SysConfig>> list(@Validated SysConfigQuery query) {
        return Result.success(sysConfigService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "使用编码获取系统配置列表")
    @EasyLog(module = "使用编码获取系统配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/listByCode")
    public Result<List<SysConfig>> listByCode(@Validated ListQuery query) {
        return Result.success(sysConfigDomainService.selectListByDomainCode(query.getCode()));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取系统配置详情")
    @EasyLog(module = "获取系统配置详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<SysConfig> detail(@PathVariable Long id) {
        return Result.success(sysConfigService.detail(id));
    }

    @EasyNoRepeatSubmit
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建系统配置")
    @EasyLog(module = "创建系统配置", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody SysConfigCreateDTO dto) {
        return Result.r(sysConfigService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑系统配置")
    @EasyLog(module = "编辑系统配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysConfigUpdateDTO dto) {
        return Result.r(sysConfigService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除系统配置")
    @EasyLog(module = "删除系统配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(sysConfigService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除系统配置")
    @EasyLog(module = "批量删除系统配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(sysConfigService.deleteBatchByIds(ids));
    }

}
