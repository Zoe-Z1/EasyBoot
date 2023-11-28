package com.easy.boot.admin.sysConfig.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置 实体
*/
@Data
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "系统模板配置视图")
public class SysTemplateConfigVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "模板配置域ID")
    private Long domainId;

    @Schema(title = "系统配置编码")
    private String code;

    @Schema(title = "系统配置value值")
    private String value;

    @Schema(title = "系统配置名称")
    private String name;

    @Schema(title = "是否必填 # 1：必填，2：非必填")
    private Integer required;

    @Schema(title = "校验描述")
    private String message;

    @Schema(title = "占位符")
    private String placeholder;

}
