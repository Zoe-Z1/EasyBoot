package com.easy.boot.admin.dataDictDomain.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 数据字典域 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDictDomainQuery对象", description = "数据字典域")
public class DataDictDomainQuery extends BasePageQuery {

    @ApiModelProperty("字典域编码")
    private String code;

    @ApiModelProperty("字典域名称")
    private String name;

    @ApiModelProperty("字典域状态 1：正常 2：禁用")
    private Integer status;

}
