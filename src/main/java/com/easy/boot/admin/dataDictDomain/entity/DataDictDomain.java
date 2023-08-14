package com.easy.boot.admin.dataDictDomain.entity;

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
* @date 2023/08/01
* @description 数据字典域 实体
*/
@Data
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_dict_domain")
@ApiModel(value = "DataDictDomain对象", description = "数据字典域")
public class DataDictDomain extends BaseEntity {

    @ColumnWidth(40)
    @ExcelProperty(value = "字典域编码")
    @ApiModelProperty("字典域编码")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "字典域名称")
    @ApiModelProperty("字典域名称")
    @TableField("name")
    private String name;

    @ExcelProperty(value = "字典域状态-正常/禁用", converter = IntegerStatusToStringConvert.class)
    @ApiModelProperty("字典域状态 1：正常 2：禁用")
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
