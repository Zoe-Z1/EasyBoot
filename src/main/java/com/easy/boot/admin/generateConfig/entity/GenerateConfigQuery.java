package com.easy.boot.admin.generateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotEmpty;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置查询实体
 */
@Schema(title = "代码生成参数配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateConfigQuery {


    @NotEmpty(message = "表名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "表名称")
    private String tableName;
}
