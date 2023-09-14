package com.easy.boot.admin.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户重置密码实体")
public class ResetPasswordDTO {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private Long id;

    @NotBlank(message = "密码不能为空")
    @Length(max = 100, message = "密码长度过长")
    @ApiModelProperty(required = true, value = "密码")
    private String password;
}
