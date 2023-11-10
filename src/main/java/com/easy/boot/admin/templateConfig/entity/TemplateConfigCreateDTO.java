package com.easy.boot.admin.templateConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置创建实体
 */
@ApiModel(value = "模板配置创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class TemplateConfigCreateDTO {


    @ApiModelProperty(required = true, value = "模板配置名称")
    @NotBlank(message = "模板配置名称不能为空")
    private String name;

    @Range(min = 1, max = 2, message = "模板配置状态不正确")
    @ApiModelProperty(required = true, value = "模板配置状态 #1：正常， 2：禁用")
    @NotNull(message = "模板配置状态不能为空")
    private Integer status;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("排序")
    private Integer sort;

}
