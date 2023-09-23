package com.easy.boot.admin.department.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 部门 Query
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DepartmentQuery对象", description = "部门")
public class DepartmentTreeQuery {

    @NotNull(message = "父级部门不能为空")
    @ApiModelProperty(required = true, value = "父级部门ID，为0则代表最上级部门")
    private Long parentId;

}
