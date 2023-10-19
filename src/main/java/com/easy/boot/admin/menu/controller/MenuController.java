package com.easy.boot.admin.menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.easy.boot.admin.menu.entity.*;
import com.easy.boot.admin.menu.service.IMenuService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatIgnore;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 菜单 前端控制器
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Resource
    private IMenuService menuService;


    @SaCheckPermission(value = "system:menu:tree")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取菜单树")
    @EasyLog(module = "获取菜单树", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/tree")
    public Result<List<MenuTree>> tree(@Validated MenuTreeQuery query) {
        return Result.success(menuService.treeList(query));
    }

    @EasyNoRepeatIgnore
    @SaCheckPermission(value = "system:menu:list")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "懒加载菜单树")
    @EasyLog(module = "懒加载菜单树", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/list")
    public Result<List<MenuLazyVO>> list(@Validated MenuTreeLazyQuery query) {
        return Result.success(menuService.selectList(query));
    }

    @SaCheckPermission(value = "system:menu:detail")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取菜单详情")
    @EasyLog(module = "获取菜单详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Menu> detail(@PathVariable Long id) {
        return Result.success(menuService.detail(id));
    }

    @SaCheckPermission(value = "system:menu:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建菜单")
    @EasyLog(module = "创建菜单", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody MenuCreateDTO dto) {
        return Result.r(menuService.create(dto));
    }

    @SaCheckPermission(value = "system:menu:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑菜单")
    @EasyLog(module = "编辑菜单", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody MenuUpdateDTO dto) {
        return Result.r(menuService.updateById(dto));
    }

    @SaCheckPermission(value = "system:menu:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除菜单")
    @EasyLog(module = "删除菜单", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(menuService.deleteById(id));
    }


}
