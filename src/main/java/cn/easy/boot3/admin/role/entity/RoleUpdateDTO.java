package cn.easy.boot3.admin.role.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Role对象", description = "角色")
public class RoleUpdateDTO extends RoleCreateDTO {

    @NotNull(message = "角色ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "角色ID")
    private Long id;

    @NotNull(message = "是否为状态切换不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "是否为状态切换")
    private Boolean isStatusChange;

}
