package cn.easy.boot3.admin.user.entity;

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
@Schema(title = "用户修改密码实体")
public class EditPasswordDTO {

    @NotNull(message = "ID不能为空")
    @Schema(title = "ID")
    private Long id;

    @NotBlank(message = "旧密码不能为空")
    @Length(max = 100, message = "旧密码长度过长")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(max = 100, message = "新密码长度过长")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "新密码")
    private String newPassword;
}
