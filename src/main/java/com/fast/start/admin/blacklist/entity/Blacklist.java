package com.fast.start.admin.blacklist.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.start.common.base.BaseEntity;
import com.fast.start.common.excel.converter.IntegerBlacklistTypeToStringConvert;
import com.fast.start.common.excel.converter.LongBlacklistDurationToStringConvert;
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
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("blacklist")
@ApiModel(value = "Blacklist对象", description = "黑名单")
public class Blacklist extends BaseEntity {

    @ExcelProperty(value = "拉黑用户账号")
    @ApiModelProperty("拉黑用户账号")
    @TableField("username")
    private String username;

    @ExcelProperty(value = "拉黑类型", converter = IntegerBlacklistTypeToStringConvert.class)
    @ApiModelProperty("类型 1：账号 2：IP")
    @TableField("type")
    private Integer type;

    @ColumnWidth(40)
    @ExcelProperty(value = "关联数据-IP地址或用户编号")
    @ApiModelProperty("关联数据  IP地址或账号ID")
    @TableField("relevance_data")
    private String relevanceData;

    @ExcelProperty(value = "拉黑时长/分钟", converter = LongBlacklistDurationToStringConvert.class)
    @ApiModelProperty("拉黑时长/分钟  -1代表永久")
    @TableField("duration")
    private Long duration;
}
