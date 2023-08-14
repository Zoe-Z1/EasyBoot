package com.easy.boot.admin.department.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Department对象", description = "部门")
public class DepartmentCreateDTO {

    @NotNull(message = "父级部门不能为空")
    @ApiModelProperty(required = true, value = "父级部门ID，为0则代表最上级部门")
    private Long parentId;

    @Length(min = 1, max = 20, message = "部门名称在{min}-{max}在{min}-{max}个字符之间")
    @NotNull(message = "部门名称不能为空")
    @ApiModelProperty(required = true, value = "部门名称")
    private String name;

    @Range(min = 1, max = 2, message = "部门状态不正确")
    @ApiModelProperty(required = false, value = "部门状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty(required = false, value = "排序")
    private Integer sort;

    @ApiModelProperty(required = false, value = "部门备注")
    private String remarks;
}
