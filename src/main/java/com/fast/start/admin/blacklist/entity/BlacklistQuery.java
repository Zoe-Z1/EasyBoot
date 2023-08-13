package com.fast.start.admin.blacklist.entity;

import com.fast.start.common.base.BasePageQuery;
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

    @ApiModelProperty("关联数据  IP地址或账号ID")
    private String relevanceData;

    @ApiModelProperty("拉黑用户账号")
    private String username;

}
