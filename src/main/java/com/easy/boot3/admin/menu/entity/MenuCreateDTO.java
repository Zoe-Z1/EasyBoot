package com.easy.boot3.admin.menu.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/30
* @description 菜单 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Menu对象", description = "菜单")
public class MenuCreateDTO {

    @NotNull(message = "父级菜单不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "父级菜单ID，为0则代表最上级菜单")
    private Long parentId;

    @Schema(title = "菜单图标")
    private String icon;

    @NotBlank(message = "菜单名称不能为空")
    @Length(min = 1, max = 20, message = "菜单名称在{min}-{max}个字符之间")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "菜单名称")
    private String label;

    @Length(min = 1, max = 50, message = "组件路径在{min}-{max}个字符之间")
    @Schema(title = "组件路径")
    private String component;

    @Length(min = 1, max = 50, message = "路由地址在{min}-{max}个字符之间")
    @Schema(title = "路由地址")
    private String path;

    @Schema(title = "路由名称")
    private String name;

    @Length(min = 1, max = 100, message = "权限字符在{min}-{max}个字符之间")
    @Schema(title = "权限字符")
    private String permission;

    @NotNull(message = "菜单类型不能为空")
    @Range(min = 1, max = 3, message = "菜单类型不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "菜单类型 1：目录  2：菜单 3：接口")
    private Integer type;

    @Range(min = 1, max = 2, message = "菜单状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "菜单状态 1：正常 2：禁用")
    private Integer status;

    @Range(min = 1, max = 2, message = "显示状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "显示状态 1：显示 2：隐藏")
    private Integer showStatus;

    @Range(min = 1, max = 2, message = "是否固钉类型不正确")
    @Schema(title = "是否固钉 #1：固定， 2：不固定")
    private Integer affix;

    @Range(min = 1, max = 2, message = "是否缓存类型不正确")
    @Schema(title = "是否缓存 #1：缓存， 2：不缓存")
    private Integer cache;

    @Range(min = 1, max = 2, message = "是否外链类型不正确")
    @Schema(title = "是否外链	# 1：是，2：否")
    private Integer isLink;

    @Schema(title = "高亮路由")
    private String activeMenu;

    @Schema(title = "排序")
    private Integer sort;

    @Schema(title = "备注")
    private String remarks;
}
