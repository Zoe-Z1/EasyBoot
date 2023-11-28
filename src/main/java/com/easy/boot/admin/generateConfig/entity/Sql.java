package com.easy.boot.admin.generateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "代码生成Sql参数对象")
public class Sql {

    @Schema(title = "是否生成")
    private Boolean enable = true;

    @Schema(title = "是否执行")
    private Boolean execute = false;

}
