package com.easy.boot.admin.blacklist.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
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
* @description 黑名单 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("blacklist")
@ApiModel(value = "Blacklist对象", description = "黑名单")
public class Blacklist extends BaseEntity {

    @ApiModelProperty("类型 1：账号 2：IP")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("关联数据  IP地址或用户账号")
    @TableField("relevance_data")
    private String relevanceData;

    @ApiModelProperty("拉黑结束时间  0代表永久")
    @TableField("end_time")
    private Long endTime;

    @ApiModelProperty("拉黑状态 1：拉黑中 2：已失效")
    @TableField("status")
    private Integer status;
}
