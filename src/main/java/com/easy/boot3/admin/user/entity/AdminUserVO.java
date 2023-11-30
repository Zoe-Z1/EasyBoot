package com.easy.boot3.admin.user.entity;

import com.easy.boot3.common.Jackson.ToStringListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(title = "用户视图实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class AdminUserVO extends AdminUser {

    @Schema(title = "是否超级管理员")
    private Boolean isAdmin;

    @JsonSerialize(using = ToStringListSerializer.class)
    @Schema(title = "角色ID集合")
    private List<Long> roleIds;

    @JsonSerialize(using = ToStringListSerializer.class)
    @Schema(title = "岗位ID集合")
    private List<Long> postIds;

}
