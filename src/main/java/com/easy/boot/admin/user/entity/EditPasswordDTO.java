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
@ApiModel("用户修改密码实体")
public class EditPasswordDTO {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private Long id;

    @NotBlank(message = "旧密码不能为空")
    @Length(max = 100, message = "旧密码长度过长")
    @ApiModelProperty(required = true, value = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(max = 100, message = "新密码长度过长")
    @ApiModelProperty(required = true, value = "新密码")
    private String newPassword;
}
