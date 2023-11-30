package com.easy.boot3.admin.dataDict.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;

/**
 * @author zoe
 * @date 2023/8/1
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DataDictQuery对象", description = "数据字典")
public class ListQuery {

    @NotBlank(message = "字典域编码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "字典域编码")
    private String code;
}
