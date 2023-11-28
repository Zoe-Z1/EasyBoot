package com.easy.boot.admin.sysConfigDomain.entity;

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
* @description 系统配置域 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SysConfigDomain对象", description = "系统配置域")
public class SysConfigDomainCreateDTO {

    @Length(min = 1, max = 50, message = "系统配置域编码在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置域编码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域编码")
    private String code;

    @Length(min = 1, max = 50, message = "系统配置域名称在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置域名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域名称")
    private String name;

    @NotNull(message = "系统配置域状态不能为空")
    @Range(min = 1, max = 2, message = "系统配置域状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域状态 1：正常 2：禁用")
    private Integer status;

    @NotNull(message = "系统配置域类型不能为空")
    @Range(min = 1, max = 2, message = "系统配置域类型不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域类型 #1：自定义配置， 2：模板配置")
    private Integer type;

    @Schema(title = "跳转组件路径")
    private String path;

    @Schema(title = "关联模板ID")
    private Long templateId;

    @NotNull(message = "排序不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "排序")
    private Integer sort;

    @Schema(title = "备注")
    private String remarks;
}
