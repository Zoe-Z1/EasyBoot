package com.easy.boot.admin.templateConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置编辑实体
 */
@ApiModel(value = "模板配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class TemplateConfigUpdateDTO extends TemplateConfigCreateDTO {


    @ApiModelProperty(required = true, value = "ID")
    @NotNull(message = "模板配置ID不能为空")
    private Long id;

}
