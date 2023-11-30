package com.easy.boot3.admin.sysConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SysConfig对象", description = "系统配置")
public class SysConfigCreateDTO {

    @NotNull(message = "系统配置域ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域ID")
    private Long domainId;

    @Length(min = 1, max = 50, message = "系统配置编码在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置编码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置编码")
    private String code;

    @Length(min = 1, max = 100, message = "系统配置值在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置值不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置值")
    private String value;

    @Length(min = 1, max = 20, message = "系统配置名称在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置名称")
    private String name;

    @NotNull(message = "系统配置状态不能为空")
    @Range(min = 1, max = 2, message = "系统配置状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "备注")
    private String remarks;

    @Schema(title = "排序")
    private Integer sort;
}
