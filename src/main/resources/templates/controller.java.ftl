package ${package.Controller};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Service}.${table.serviceName};
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
<#else>
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.fast.start.common.base.Result;
import ${package.Entity}.${entity};
import ${package.Entity}.${entity}Query;
import ${package.Entity}.${entity}CreateDTO;
import ${package.Entity}.${entity}UpdateDTO;
import com.fast.start.common.log.FastLog;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
<#if swagger>
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
</#if>
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ${author}
 * @date ${date}
 * @description ${table.comment!} 前端控制器
 */
@Slf4j
<#if swagger>
@Api(tags = "${table.comment!}接口")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if requestBasePath?? && requestBasePath != "">/${requestBasePath}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${package.ModuleName}Service;


    @ApiOperationSupport(author = "${author}")
    @ApiOperation(value = "获取${table.comment!}列表")
    @FastLog(module = "获取${table.comment!}列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<${entity}>> page(@Validated ${entity}Query query) {
        return Result.success(${package.ModuleName}Service.selectPage(query));
    }

    @ApiOperationSupport(author = "${author}")
    @ApiOperation(value = "获取${table.comment!}详情")
    @FastLog(module = "获取${table.comment!}详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<${entity}> detail(@PathVariable Long id) {
        return Result.success(${package.ModuleName}Service.detail(id));
    }

    @ApiOperationSupport(author = "${author}")
    @ApiOperation(value = "创建${table.comment!}")
    @FastLog(module = "创建${table.comment!}", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody ${entity}CreateDTO dto) {
        return Result.r(${package.ModuleName}Service.create(dto));
    }

    @ApiOperationSupport(author = "${author}")
    @ApiOperation(value = "编辑${table.comment!}")
    @FastLog(module = "编辑${table.comment!}", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody ${entity}UpdateDTO dto) {
        return Result.r(${package.ModuleName}Service.updateById(dto));
    }

    @ApiOperationSupport(author = "${author}")
    @ApiOperation(value = "删除${table.comment!}")
    @FastLog(module = "删除${table.comment!}", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(${package.ModuleName}Service.deleteById(id));
    }

    @ApiOperationSupport(author = "${author}")
    @ApiOperation(value = "批量删除${table.comment!}")
    @FastLog(module = "批量删除${table.comment!}", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(${package.ModuleName}Service.deleteBatchByIds(ids));
    }

}
</#if>
