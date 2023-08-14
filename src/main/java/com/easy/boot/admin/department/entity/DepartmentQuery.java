package com.easy.boot.admin.department.entity;

import com.easy.boot.common.base.BasePageQuery;
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
public class DepartmentQuery extends BasePageQuery {

    @NotNull(message = "父级部门不能为空")
    @ApiModelProperty(required = true, value = "父级部门ID，为0则代表最上级部门")
    private Long parentId;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("部门状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
