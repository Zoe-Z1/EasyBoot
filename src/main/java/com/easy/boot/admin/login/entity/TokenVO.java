package com.easy.boot.admin.login.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Schema(title = "token返回实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    @Schema(title = "token的key")
    private String tokenName;

    @Schema(title = "鉴权token")
    private String token;
}
