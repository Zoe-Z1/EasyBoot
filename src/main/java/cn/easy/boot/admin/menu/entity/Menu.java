package cn.easy.boot.admin.menu.entity;

import cn.easy.boot.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Menu对象", description = "菜单")
public class Menu extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("父级菜单ID，为0则代表最上级菜单")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("菜单名称")
    @TableField("label")
    private String label;

    @ApiModelProperty("组件路径")
    @TableField("component")
    private String component;

    @ApiModelProperty("路由地址")
    @TableField("path")
    private String path;

    @ApiModelProperty("路由名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限字符")
    @TableField("permission")
    private String permission;

    @ApiModelProperty("菜单类型 1：目录  2：菜单 3：接口")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("菜单状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("显示状态 1：显示 2：隐藏")
    @TableField("show_status")
    private Integer showStatus;

    @ApiModelProperty("是否固钉 #1：固定， 2：不固定")
    @TableField("affix")
    private Integer affix;

    @ApiModelProperty("是否缓存 #1：缓存， 2：不缓存")
    @TableField("cache")
    private Integer cache;

    @ApiModelProperty("是否外链	# 1：是，2：否")
    @TableField("is_link")
    private Integer isLink;

    @ApiModelProperty("高亮路由")
    @TableField("active_menu")
    private String activeMenu;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;
}
