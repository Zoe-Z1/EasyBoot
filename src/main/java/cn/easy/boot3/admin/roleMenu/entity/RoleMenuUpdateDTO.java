package cn.easy.boot3.admin.roleMenu.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "RoleMenu对象", description = "角色菜单关联")
public class RoleMenuUpdateDTO {

    @Schema(title = "菜单ID")
    private Long menuId;

    @Schema(title = "角色ID")
    private Long roleId;
}
