package com.easy.boot3.admin.generateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author zoe
 * @date 2023/9/10
 * @description
 */
@Schema(title = "代码生成Table参数配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class TableConfigQuery {

    @NotEmpty(message = "表名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "表名称")
    private String tableName;

}
