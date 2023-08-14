package com.easy.boot.admin.sysConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysConfig对象", description = "系统配置")
public class SysConfigCreateDTO {

    @NotNull(message = "系统配置域ID不能为空")
    @ApiModelProperty(required = true, value = "系统配置域ID")
    private Long domainId;

    @Length(min = 1, max = 50, message = "系统配置编码在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置编码不能为空")
    @ApiModelProperty(required = true, value = "系统配置编码")
    private String code;

    @Length(min = 1, max = 100, message = "系统配置值在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置值不能为空")
    @ApiModelProperty(required = true, value = "系统配置值")
    private String value;

    @Length(min = 1, max = 20, message = "系统配置名称在{min}-{max}个字符之间")
    @NotBlank(message = "系统配置名称不能为空")
    @ApiModelProperty(required = true, value = "系统配置名称")
    private String name;

    @NotNull(message = "系统配置状态不能为空")
    @Range(min = 1, max = 2, message = "系统配置状态不正确")
    @ApiModelProperty(required = true, value = "系统配置状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty(required = false, value = "备注")
    private String remarks;

    @ApiModelProperty(required = false, value = "排序")
    private Integer sort;
}
