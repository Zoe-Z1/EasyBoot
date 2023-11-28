package com.easy.boot.admin.roleMenu.entity;

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
* @description 角色菜单关联 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_menu")
@Schema(title = "RoleMenu对象", description = "角色菜单关联")
public class RoleMenu extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "菜单ID")
    @TableField("menu_id")
    private Long menuId;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "角色ID")
    @TableField("role_id")
    private Long roleId;
}
