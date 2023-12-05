package cn.easy.boot3.admin.sysConfig.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

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
@Schema(title = "SysConfigQuery对象", description = "系统配置")
public class SysConfigQuery extends BasePageQuery {

    @NotNull(message = "系统配置域ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域ID")
    private Long domainId;

    @Schema(title = "配置编码")
    private String code;

    @Schema(title = "配置名称")
    private String name;

    @Schema(title = "配置状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
