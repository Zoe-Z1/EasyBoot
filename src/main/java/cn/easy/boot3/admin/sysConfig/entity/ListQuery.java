package cn.easy.boot3.admin.sysConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SysConfigQuery对象", description = "系统配置")
public class ListQuery {

    @NotBlank(message = "配置域编码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "配置域编码")
    private String code;
}
