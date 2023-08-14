package com.easy.boot.admin.sysConfig.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 系统配置 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysConfigQuery对象", description = "系统配置")
public class SysConfigQuery extends BasePageQuery {

    @NotNull(message = "系统配置域ID不能为空")
    @ApiModelProperty(required = true, value = "系统配置域ID")
    private Long domainId;

    @ApiModelProperty("配置编码")
    private String code;

    @ApiModelProperty("配置名称")
    private String name;

    @ApiModelProperty(required = false, value = "配置状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
