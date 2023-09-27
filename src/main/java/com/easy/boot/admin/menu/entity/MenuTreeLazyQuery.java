package com.easy.boot.admin.menu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 菜单 Query
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "MenuLazyQuery对象", description = "菜单懒加载查询")
public class MenuTreeLazyQuery {

    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty(required = false, value = "父级菜单ID，为0则代表最上级菜单")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String label;

    @ApiModelProperty("权限字符")
    private String permission;

    @Range(min = 1, max = 3, message = "菜单类型不正确")
    @ApiModelProperty("菜单类型 1：目录  2：菜单 3：按钮")
    private Integer type;

    @Range(min = 1, max = 2, message = "菜单状态不正确")
    @ApiModelProperty("菜单状态 1：正常 2：禁用")
    private Integer status;

    @Range(min = 1, max = 2, message = "显示状态不正确")
    @ApiModelProperty("显示状态 1：显示 2：隐藏")
    private Integer showStatus;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}