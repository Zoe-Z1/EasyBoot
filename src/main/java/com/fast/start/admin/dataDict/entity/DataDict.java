package com.fast.start.admin.dataDict.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.start.common.base.BaseEntity;
import com.fast.start.common.excel.converter.IntegerStatusToStringConvert;
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
* @description 数据字典 实体
*/
@Data
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_dict")
@ApiModel(value = "DataDict对象", description = "数据字典")
public class DataDict extends BaseEntity {

    @ExcelIgnore
    @ApiModelProperty("字典域ID")
    @TableField("domain_id")
    private Long domainId;

    @ExcelProperty(value = "字典键")
    @ApiModelProperty("字典键")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "字典标签")
    @ApiModelProperty("字典标签")
    @TableField("label")
    private String label;

    @ExcelProperty(value = "字典状态-正常/禁用", converter = IntegerStatusToStringConvert.class)
    @ApiModelProperty("字典状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    @ExcelProperty(value = "排序")
    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;
}
