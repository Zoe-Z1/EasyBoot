package com.easy.boot.admin.userRole.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 用户角色关联 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UserRoleQuery对象", description = "用户角色关联")
public class UserRoleQuery extends BasePageQuery {

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "角色ID")
    private Long roleId;
}
