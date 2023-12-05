package cn.easy.boot.admin.roleMenu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 角色菜单关联 Query
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoleMenuQuery对象", description = "角色菜单关联")
public class RoleMenuQuery {

    @ApiModelProperty("菜单ID")
    private Long menuId;

    @ApiModelProperty("角色ID")
    private Long roleId;
}
