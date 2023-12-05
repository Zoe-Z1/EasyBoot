package cn.easy.boot.admin.sysConfigDomain.entity;

import cn.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "SysConfigDomainQuery对象", description = "系统配置域")
public class SysConfigDomainQuery extends BasePageQuery {

    @ApiModelProperty("域编码")
    private String code;

    @ApiModelProperty("域名称")
    private String name;

    @ApiModelProperty("系统配置域状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
