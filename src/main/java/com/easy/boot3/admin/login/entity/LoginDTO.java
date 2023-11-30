package com.easy.boot3.admin.login.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@Schema(title = "用户登录DTO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "账号不能为空")
    @Length(min = 5, max = 15, message = "账号在{min}-{max}个字符之间")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账号格式不正确，仅支持字母、数字和下划线")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "密码")
    private String password;

    @NotBlank(message = "验证码ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "验证码ID")
    private String id;

}
