package cn.easy.boot.admin.userRole.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/30
* @description 用户角色关联 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserRole对象", description = "用户角色关联")
public class UserRoleUpdateDTO {

    @ApiModelProperty(required = false, value = "用户ID")
    private Long userId;

    @ApiModelProperty(required = false, value = "角色ID")
    private Long roleId;
}
