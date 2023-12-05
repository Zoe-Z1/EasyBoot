package cn.easy.boot3.admin.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

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
@Schema(title = "用户状态变更实体")
public class AdminUserDisabledDTO {

    @NotNull(message = "ID不能为空")
    @Schema(title = "ID")
    private Long id;

    @NotNull(message = "账号状态不能为空")
    @Range(min = 1, max = 2, message = "账号状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "账号状态 1：正常，2：禁用")
    private Integer status;

}
