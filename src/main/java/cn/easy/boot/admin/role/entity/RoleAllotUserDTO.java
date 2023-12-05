package cn.easy.boot.admin.role.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "RoleAllotUser对象", description = "角色分配用户")
public class RoleAllotUserDTO {

    @NotEmpty(message = "用户ID不能为空")
    @ApiModelProperty(required = true, value = "用户ID集合")
    private List<Long> userIds;

    @NotNull(message = "角色D不能为空")
    @ApiModelProperty(required = true, value = "角色ID不能为空")
    private Long roleId;
}
