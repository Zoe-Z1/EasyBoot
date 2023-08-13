package com.fast.start.admin.login.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@ApiModel("用户登录DTO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(required = true, value = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(required = true, value = "密码")
    private String password;

}
