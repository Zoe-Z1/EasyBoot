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
@Schema(title = "代码生成UpdateDTO参数对象")
public class UpdateDTO {

    @Schema(title = "是否生成")
    private Boolean enable = true;

    @Schema(title = "是否继承createDTO")
    private Boolean enableExtendsCreateDTO = true;

}