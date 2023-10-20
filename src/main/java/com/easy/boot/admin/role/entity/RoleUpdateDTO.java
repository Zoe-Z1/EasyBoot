package com.easy.boot.admin.role.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "Role对象", description = "角色")
public class RoleUpdateDTO extends RoleCreateDTO {

    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(required = true, value = "角色ID")
    private Long id;

    @NotNull(message = "是否为状态切换不能为空")
    @ApiModelProperty(required = true, value = "是否为状态切换")
    private Boolean isStatusChange;

}
