package com.easy.boot.admin.sysConfigDomain.controller;

import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomain;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainCreateDTO;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainUpdateDTO;
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
 * @description 系统配置域 前端控制器
 */
@Slf4j
@Api(tags = "系统配置域接口")
@RestController
@RequestMapping("/admin/sysConfigDomain")
public class SysConfigDomainController extends BaseController {

    @Resource
    private ISysConfigDomainService sysConfigDomainService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取系统配置域列表")
    @EasyLog(module = "获取系统配置域列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/list")
    public Result<List<SysConfigDomain>> list() {
        return Result.success(sysConfigDomainService.selectList());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取系统配置域详情")
    @EasyLog(module = "获取系统配置域详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<SysConfigDomain> detail(@PathVariable Long id) {
        return Result.success(sysConfigDomainService.detail(id));
    }

    @EasyNoRepeatSubmit
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建系统配置域")
    @EasyLog(module = "创建系统配置域", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody SysConfigDomainCreateDTO dto) {
        return Result.r(sysConfigDomainService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑系统配置域")
    @EasyLog(module = "编辑系统配置域", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysConfigDomainUpdateDTO dto) {
        return Result.r(sysConfigDomainService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除系统配置域")
    @EasyLog(module = "删除系统配置域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(sysConfigDomainService.deleteById(id));
    }

}
