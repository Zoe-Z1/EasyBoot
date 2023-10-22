package com.easy.boot.admin.user.entity;

import com.easy.boot.common.Jackson.ToStringListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户视图实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class AdminUserVO extends AdminUser {

    @ApiModelProperty("是否超级管理员")
    private Boolean isAdmin;

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("角色ID集合")
    private List<Long> roleIds;

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("岗位ID集合")
    private List<Long> postIds;

}
