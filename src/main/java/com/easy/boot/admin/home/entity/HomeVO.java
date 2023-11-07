package com.easy.boot.admin.home.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@ApiModel("token返回实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeVO {

    @ApiModelProperty("token的key")
    private String tokenName;

    @ApiModelProperty("鉴权token")
    private String token;
}
