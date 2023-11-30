package com.easy.boot3.admin.templateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置编辑实体
 */
@Schema(title = "模板配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class TemplateConfigUpdateDTO extends TemplateConfigCreateDTO {


    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "ID")
    @NotNull(message = "模板配置ID不能为空")
    private Long id;

}
