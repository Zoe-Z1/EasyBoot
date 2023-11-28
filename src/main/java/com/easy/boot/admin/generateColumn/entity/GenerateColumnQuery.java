package com.easy.boot.admin.generateColumn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotEmpty;


/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置查询实体
 */
@TableName("generate_column")
@Schema(title = "代码生成列配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateColumnQuery {


    @NotEmpty(message = "表名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "表名称")
    private String tableName;

}
