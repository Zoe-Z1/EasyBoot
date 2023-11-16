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
@ApiModel("代码生成UpdateDTO参数对象")
public class UpdateDTO {

    @ApiModelProperty("是否生成")
    private Boolean enable = true;

    @ApiModelProperty("是否继承createDTO")
    private Boolean enableExtendsCreateDTO = true;

}
