package com.easy.boot.admin.department.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.easy.boot.admin.department.entity.DepartmentCreateDTO;
import com.easy.boot.admin.department.entity.DepartmentLazyQuery;
import com.easy.boot.admin.department.entity.DepartmentLazyVO;
import com.easy.boot.admin.department.entity.DepartmentUpdateDTO;
import com.easy.boot.admin.department.service.IDepartmentService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatIgnore;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 部门 前端控制器
 */
@Slf4j
@Api(tags = "部门接口")
@RestController
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController {

    @Resource
    private IDepartmentService departmentService;



    @EasyNoRepeatIgnore
    @SaCheckPermission(value = "system:department:list")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "懒加载部门树")
    @EasyLog(module = "懒加载部门树", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/list")
    public Result<List<DepartmentLazyVO>> list(@Validated DepartmentLazyQuery query) {
        return Result.success(departmentService.selectList(query));
    }

    @SaCheckPermission(value = "system:department:parent:ids")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取父级部门列表")
    @EasyLog(module = "获取父级部门列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/parentIds/{id}")
    public Result<List<String>> parentIds(@PathVariable Long id) {
        List<Long> parentIds = departmentService.getParentIds(id);
        List<String> ids = parentIds.stream().map(x -> x + "").collect(Collectors.toList());
        return Result.success(ids);
    }

    @SaCheckPermission(value = "system:department:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建部门")
    @EasyLog(module = "创建部门", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DepartmentCreateDTO dto) {
        return Result.r(departmentService.create(dto));
    }

    @SaCheckPermission(value = "system:department:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑部门")
    @EasyLog(module = "编辑部门", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DepartmentUpdateDTO dto) {
        return Result.r(departmentService.updateById(dto));
    }

    @SaCheckPermission(value = "system:department:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除部门")
    @EasyLog(module = "删除部门", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(departmentService.deleteById(id));
    }

}
