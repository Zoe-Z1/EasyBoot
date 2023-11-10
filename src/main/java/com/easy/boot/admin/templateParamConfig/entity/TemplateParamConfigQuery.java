package com.easy.boot.admin.templateParamConfig.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置查询实体
 */
@ApiModel(value = "模板参数配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemplateParamConfigQuery extends BasePageQuery {

    @ApiModelProperty(required = true, value = "模板ID")
    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    @ApiModelProperty("模板参数键")
    private String code;

    @ApiModelProperty("模板参数名称")
    private String name;

    @ApiModelProperty("是否必填 # 1：必填，2：非必填")
    private Integer required;

    @ApiModelProperty("模板配置状态 #1：正常， 2：禁用")
    private Integer status;

}
