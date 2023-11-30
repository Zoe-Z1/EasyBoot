package com.easy.boot3.admin.sysConfigDomain.entity;

import com.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 系统配置域 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SysConfigDomainQuery对象", description = "系统配置域")
public class SysConfigDomainQuery extends BasePageQuery {

    @Schema(title = "域编码")
    private String code;

    @Schema(title = "域名称")
    private String name;

    @Schema(title = "系统配置域状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
