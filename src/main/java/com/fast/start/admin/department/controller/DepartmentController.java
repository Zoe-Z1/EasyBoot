package com.fast.start.admin.department.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.department.entity.*;
import com.fast.start.admin.department.service.IDepartmentService;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.log.FastLog;
import com.fast.start.common.properties.FastFile;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private FastFile fastFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    private Integer maxLimit;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取树形部门")
    @FastLog(module = "获取树形部门", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/tree")
    public Result<List<DepartmentTree>> tree(@Validated DepartmentTreeQuery query) {
        return Result.success(departmentService.treeList(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取部门列表")
    @FastLog(module = "获取部门列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Department>> page(@Validated DepartmentQuery query) {
        return Result.success(departmentService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取部门详情")
    @FastLog(module = "获取部门详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Department> detail(@PathVariable Long id) {
        return Result.success(departmentService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建部门")
    @FastLog(module = "创建部门", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DepartmentCreateDTO dto) {
        return Result.r(departmentService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑部门")
    @FastLog(module = "编辑部门", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DepartmentUpdateDTO dto) {
        return Result.r(departmentService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除部门")
    @FastLog(module = "删除部门", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(departmentService.deleteById(id));
    }

}
