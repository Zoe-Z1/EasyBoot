package com.easy.boot.admin.generateConfig.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置编辑实体
 */
@TableName("generate_config")
@ApiModel(value = "代码生成参数配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenerateConfigUpdateDTO extends GenerateConfigCreateDTO {

    @NotNull(message = "主键ID不能为空")
    @ApiModelProperty("主键ID")
    private Long id;
}
