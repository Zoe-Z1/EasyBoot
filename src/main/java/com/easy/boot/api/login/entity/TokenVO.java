package com.easy.boot.api.login.entity;

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
@ApiModel("Token返回实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    @ApiModelProperty("鉴权token")
    private String token;
}
