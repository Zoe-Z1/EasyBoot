package com.easy.boot3.admin.dataDictDomain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot3.common.base.BaseEntity;
import com.easy.boot3.common.excel.EasyExcelSelect;
import com.easy.boot3.common.excel.converter.IntegerStatusToStringConvert;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "DataDictDomain对象", description = "数据字典域")
public class DataDictDomain extends BaseEntity {

    @ColumnWidth(40)
    @ExcelProperty(value = "字典域编码")
    @Schema(title = "字典域编码")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "字典域名称")
    @Schema(title = "字典域名称")
    @TableField("name")
    private String name;

    @EasyExcelSelect(code = "dict_status")
    @ExcelProperty(value = "字典域状态", converter = IntegerStatusToStringConvert.class)
    @Schema(title = "字典域状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "备注")
    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;

    @ExcelProperty(value = "排序")
    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;
}
