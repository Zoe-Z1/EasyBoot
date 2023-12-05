package cn.easy.boot3.admin.sysConfigDomain.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.sysConfigDomain.entity.SysConfigDomain;
import cn.easy.boot3.admin.sysConfigDomain.service.ISysConfigDomainService;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.admin.sysConfig.entity.SysConfig;
import cn.easy.boot3.admin.sysConfigDomain.entity.SysConfigDomainCreateDTO;
import cn.easy.boot3.admin.sysConfigDomain.entity.SysConfigDomainQuery;
import cn.easy.boot3.admin.sysConfigDomain.entity.SysConfigDomainUpdateDTO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 系统配置域 前端控制器
 */
@Slf4j
@Tag(name = "系统配置域接口")
@RestController
@RequestMapping("/admin/sysConfigDomain")
public class SysConfigDomainController extends BaseController {

    @Resource
    private ISysConfigDomainService sysConfigDomainService;


    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取全部全局配置")
    @EasyLog(module = "获取全部全局配置", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/global/all")
    public Result<List<SysConfig>> globalAll() {
        return Result.success(sysConfigDomainService.selectGlobalAll());
    }

    @SaCheckPermission(value = "system:sys:config:domain:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取系统配置域列表")
    @EasyLog(module = "分页获取系统配置域列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<SysConfigDomain>> page(@Validated SysConfigDomainQuery query) {
        return Result.success(sysConfigDomainService.selectPage(query));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:sys:config:domain:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建系统配置域")
    @EasyLog(module = "创建系统配置域", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody SysConfigDomainCreateDTO dto) {
        return Result.r(sysConfigDomainService.create(dto));
    }

    @SaCheckPermission(value = "system:sys:config:domain:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑系统配置域")
    @EasyLog(module = "编辑系统配置域", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysConfigDomainUpdateDTO dto) {
        return Result.r(sysConfigDomainService.updateById(dto));
    }

    @SaCheckPermission(value = "system:sys:config:domain:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除系统配置域")
    @EasyLog(module = "删除系统配置域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(sysConfigDomainService.deleteById(id));
    }

}
