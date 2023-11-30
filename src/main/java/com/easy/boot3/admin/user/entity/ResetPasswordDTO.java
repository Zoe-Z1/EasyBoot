package com.easy.boot3.admin.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "用户重置密码实体")
public class ResetPasswordDTO {

    @NotNull(message = "ID不能为空")
    @Schema(title = "ID")
    private Long id;

    @NotBlank(message = "密码不能为空")
    @Length(max = 100, message = "密码长度过长")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "密码")
    private String password;
}
