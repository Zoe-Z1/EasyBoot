package com.easy.boot.admin.generate.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成实体
 */
@ApiModel(value = "代码生成实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateCode {

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "文件生成路径")
    private String genPath;

    @ApiModelProperty(value = "文件内容")
    private String fileContent;

    @ApiModelProperty(value = "是否执行SQL")
    private Boolean execute;

}
