package com.easy.boot.admin.templateParamConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置编辑实体
 */
@Schema(title = "模板参数配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class TemplateParamConfigUpdateDTO extends TemplateParamConfigCreateDTO {


    @NotNull(message = "模板参数配置ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "ID")
    private Long id;

}
