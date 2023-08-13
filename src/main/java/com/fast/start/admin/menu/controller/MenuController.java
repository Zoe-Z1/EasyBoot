package com.fast.start.admin.menu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.menu.entity.*;
import com.fast.start.admin.menu.service.IMenuService;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.log.FastLog;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
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


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取树形菜单")
    @FastLog(module = "获取树形菜单", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/tree")
    public Result<List<MenuTree>> tree(@Validated MenuTreeQuery query) {
        return Result.success(menuService.treeList(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取菜单列表")
    @FastLog(module = "获取菜单列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Menu>> page(@Validated MenuQuery query) {
        return Result.success(menuService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取菜单详情")
    @FastLog(module = "获取菜单详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Menu> detail(@PathVariable Long id) {
        return Result.success(menuService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建菜单")
    @FastLog(module = "创建菜单", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody MenuCreateDTO dto) {
        return Result.r(menuService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑菜单")
    @FastLog(module = "编辑菜单", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody MenuUpdateDTO dto) {
        return Result.r(menuService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除菜单")
    @FastLog(module = "删除菜单", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(menuService.deleteById(id));
    }


}
