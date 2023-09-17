package com.easy.boot.admin.generateConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成Sql参数对象")
public class Sql {

    @ApiModelProperty("是否生成")
    private Boolean enable = true;

    @ApiModelProperty("是否执行")
    private Boolean execute = false;

}
