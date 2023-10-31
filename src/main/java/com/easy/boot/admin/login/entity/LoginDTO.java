package com.easy.boot.admin.login.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Length(min = 5, max = 15, message = "账号在{min}-{max}个字符之间")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账号格式不正确，仅支持字母、数字和下划线")
    @ApiModelProperty(required = true, value = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(required = true, value = "密码")
    private String password;

    @NotBlank(message = "验证码ID不能为空")
    @ApiModelProperty(required = true, value = "验证码ID")
    private String id;

}
