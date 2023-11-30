package com.easy.boot3.admin.department.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.easy.boot3.admin.department.entity.DepartmentCreateDTO;
import com.easy.boot3.admin.department.entity.DepartmentLazyQuery;
import com.easy.boot3.admin.department.entity.DepartmentLazyVO;
import com.easy.boot3.admin.department.entity.DepartmentUpdateDTO;
import com.easy.boot3.admin.department.service.IDepartmentService;
import com.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot3.common.base.BaseController;
import com.easy.boot3.common.base.Result;
import com.easy.boot3.common.log.EasyLog;
import com.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 部门 前端控制器
 */
@Slf4j
@Tag(name = "部门接口")
@RestController
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController {

    @Resource
    private IDepartmentService departmentService;


    @SaCheckPermission(value = "system:department:list")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "懒加载部门树")
    @EasyLog(module = "懒加载部门树", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/list")
    public Result<List<DepartmentLazyVO>> list(@Validated DepartmentLazyQuery query) {
        return Result.success(departmentService.selectList(query));
    }

    @SaCheckPermission(value = "system:department:parent:ids")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取父级部门列表")
    @EasyLog(module = "获取父级部门列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/parentIds/{id}")
    public Result<List<String>> parentIds(@PathVariable Long id) {
        List<Long> parentIds = departmentService.getParentIds(id);
        List<String> ids = parentIds.stream().map(x -> x + "").collect(Collectors.toList());
        return Result.success(ids);
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:department:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建部门")
    @EasyLog(module = "创建部门", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DepartmentCreateDTO dto) {
        return Result.r(departmentService.create(dto));
    }

    @SaCheckPermission(value = "system:department:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑部门")
    @EasyLog(module = "编辑部门", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DepartmentUpdateDTO dto) {
        return Result.r(departmentService.updateById(dto));
    }

    @SaCheckPermission(value = "system:department:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除部门")
    @EasyLog(module = "删除部门", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(departmentService.deleteById(id));
    }

}
