package cn.easy.boot3.admin.menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.menu.entity.*;
import cn.easy.boot3.admin.menu.service.IMenuService;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import cn.easy.boot3.admin.menu.entity.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 菜单 前端控制器
 */
@Tag(name = "菜单接口")
@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Resource
    private IMenuService menuService;


    @SaCheckPermission(value = "system:menu:tree")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取菜单树")
    @EasyLog(module = "获取菜单树", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/tree")
    public Result<List<MenuTree>> tree(@Validated MenuTreeQuery query) {
        return Result.success(menuService.treeList(query));
    }

    @SaCheckPermission(value = "system:menu:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取菜单详情")
    @EasyLog(module = "获取菜单详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Menu> detail(@PathVariable Long id) {
        return Result.success(menuService.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:menu:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建菜单")
    @EasyLog(module = "创建菜单", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody MenuCreateDTO dto) {
        return Result.r(menuService.create(dto));
    }

    @SaCheckPermission(value = "system:menu:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑菜单")
    @EasyLog(module = "编辑菜单", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody MenuUpdateDTO dto) {
        return Result.r(menuService.updateById(dto));
    }

    @SaCheckPermission(value = "system:menu:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除菜单")
    @EasyLog(module = "删除菜单", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(menuService.deleteById(id));
    }


}
