package com.easy.boot.admin.blacklist.entity;

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
 * @description 黑名单 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "BlacklistQuery对象", description = "黑名单")
public class BlacklistQuery extends BasePageQuery {

    @ApiModelProperty("类型 1：账号 2：IP")
    private Integer type;

    @ApiModelProperty("关联数据  IP地址或用户账号")
    private String relevanceData;

    @ApiModelProperty("操作人账号")
    private String createUsername;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("拉黑状态 1：拉黑中 2：已失效")
    private Integer status;

}
