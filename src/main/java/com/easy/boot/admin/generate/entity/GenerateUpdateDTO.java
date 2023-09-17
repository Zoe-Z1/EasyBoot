package com.easy.boot.admin.generate.entity;

import com.easy.boot.admin.generateColumn.entity.GenerateColumnUpdateDTO;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigUpdateDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置编辑实体
 */
@ApiModel(value = "代码生成参数配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateUpdateDTO {

    @ApiModelProperty(required = true, value = "代码生成参数配置")
    GenerateConfigUpdateDTO generateConfig;

    @ApiModelProperty(required = true, value = "代码生成列配置")
    List<GenerateColumnUpdateDTO> generateColumns;

}
