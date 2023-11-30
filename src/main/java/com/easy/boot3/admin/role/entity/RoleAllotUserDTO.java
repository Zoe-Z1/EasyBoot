package com.easy.boot3.admin.role.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zoe
 * @date 2023/7/30
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "RoleAllotUser对象", description = "角色分配用户")
public class RoleAllotUserDTO {

    @NotEmpty(message = "用户ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "用户ID集合")
    private List<Long> userIds;

    @NotNull(message = "角色D不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "角色ID不能为空")
    private Long roleId;
}
