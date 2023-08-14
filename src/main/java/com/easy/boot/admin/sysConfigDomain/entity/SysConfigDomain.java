package com.easy.boot.admin.sysConfigDomain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.converter.IntegerStatusToStringConvert;
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
* @description 系统配置域 实体
*/
@Data
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_config_domain")
@ApiModel(value = "SysConfigDomain对象", description = "系统配置域")
public class SysConfigDomain extends BaseEntity {

    @ExcelProperty(value = "系统配置域编码")
    @ApiModelProperty("系统配置域编码")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "系统配置域名称")
    @ApiModelProperty("系统配置域名称")
    @TableField("name")
    private String name;

    @ExcelProperty(value = "系统配置域状态-正常/禁用", converter = IntegerStatusToStringConvert.class)
    @ApiModelProperty("系统配置域状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "排序")
    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;
}
