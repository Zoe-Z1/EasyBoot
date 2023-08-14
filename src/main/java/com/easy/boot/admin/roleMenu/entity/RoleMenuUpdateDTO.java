package com.easy.boot.admin.roleMenu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/30
* @description 角色菜单关联 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoleMenu对象", description = "角色菜单关联")
public class RoleMenuUpdateDTO {

    @ApiModelProperty(required = false, value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(required = false, value = "角色ID")
    private Long roleId;
}
