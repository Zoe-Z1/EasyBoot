package com.easy.boot3.admin.dataDict.entity;

import com.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 数据字典 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DataDictQuery对象", description = "数据字典")
public class DataDictQuery extends BasePageQuery {

    @NotNull(message = "字典域ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "字典域ID")
    private Long domainId;

    @Schema(title = "字典键")
    private String code;

    @Schema(title = "字典标签")
    private String label;

    @Schema(title = "字典状态 1：正常 2：禁用")
    private Integer status;

}
