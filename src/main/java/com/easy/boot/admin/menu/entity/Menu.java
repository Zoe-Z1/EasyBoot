package com.easy.boot.admin.menu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/30
* @description 菜单 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("menu")
@Schema(title = "Menu对象", description = "菜单")
public class Menu extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "父级菜单ID，为0则代表最上级菜单")
    @TableField("parent_id")
    private Long parentId;

    @Schema(title = "菜单图标")
    @TableField("icon")
    private String icon;

    @Schema(title = "菜单名称")
    @TableField("label")
    private String label;

    @Schema(title = "组件路径")
    @TableField("component")
    private String component;

    @Schema(title = "路由地址")
    @TableField("path")
    private String path;

    @Schema(title = "路由名称")
    @TableField("name")
    private String name;

    @Schema(title = "权限字符")
    @TableField("permission")
    private String permission;

    @Schema(title = "菜单类型 1：目录  2：菜单 3：接口")
    @TableField("type")
    private Integer type;

    @Schema(title = "菜单状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @Schema(title = "显示状态 1：显示 2：隐藏")
    @TableField("show_status")
    private Integer showStatus;

    @Schema(title = "是否固钉 #1：固定， 2：不固定")
    @TableField("affix")
    private Integer affix;

    @Schema(title = "是否缓存 #1：缓存， 2：不缓存")
    @TableField("cache")
    private Integer cache;

    @Schema(title = "是否外链	# 1：是，2：否")
    @TableField("is_link")
    private Integer isLink;

    @Schema(title = "高亮路由")
    @TableField("active_menu")
    private String activeMenu;

    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;
}
