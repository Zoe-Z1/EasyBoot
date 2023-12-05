package cn.easy.boot.admin.templateConfig.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot.admin.templateConfig.entity.TemplateConfig;
import cn.easy.boot.admin.templateConfig.entity.TemplateConfigCreateDTO;
import cn.easy.boot.admin.templateConfig.entity.TemplateConfigQuery;
import cn.easy.boot.admin.templateConfig.entity.TemplateConfigUpdateDTO;
import cn.easy.boot.common.base.BaseController;
import cn.easy.boot.common.base.Result;
import cn.easy.boot.common.log.EasyLog;
import cn.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot.admin.templateConfig.service.ITemplateConfigService;
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
 * @date 2023/11/09
 * @description 模板配置接口
 */
@Slf4j
@Api(tags = "模板配置接口")
@RestController
@RequestMapping("/admin/templateConfig")
public class TemplateConfigController extends BaseController {

    @Resource
    private ITemplateConfigService templateConfigService;


    @SaCheckPermission(value = "system:template:config:all")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取全部模板配置")
    @EasyLog(module = "获取全部模板配置", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/all")
    public Result<List<TemplateConfig>> all() {
        return Result.success(templateConfigService.selectAll());
    }

    @SaCheckPermission(value = "system:template:config:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取模板配置列表")
    @EasyLog(module = "分页获取模板配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<TemplateConfig>> page(@Validated TemplateConfigQuery query) {
        return Result.success(templateConfigService.selectPage(query));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:template:config:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建模板配置")
    @EasyLog(module = "创建模板配置", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody TemplateConfigCreateDTO dto) {
        return Result.r(templateConfigService.create(dto));
    }

    @SaCheckPermission(value = "system:template:config:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑模板配置")
    @EasyLog(module = "编辑模板配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody TemplateConfigUpdateDTO dto) {
        return Result.r(templateConfigService.updateById(dto));
    }

    @SaCheckPermission(value = "system:template:config:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除模板配置")
    @EasyLog(module = "删除模板配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(templateConfigService.deleteById(id));
    }

}
