package com.easy.boot.admin.templateParamConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置编辑实体
 */
@ApiModel(value = "模板参数配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class TemplateParamConfigUpdateDTO extends TemplateParamConfigCreateDTO {


    @NotNull(message = "模板参数配置ID不能为空")
    @ApiModelProperty(required = true, value = "ID")
    private Long id;

}
