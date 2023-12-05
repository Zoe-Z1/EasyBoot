package cn.easy.boot3.admin.dataDict.entity;

import cn.easy.boot3.common.base.BaseEntity;
import cn.easy.boot3.common.excel.EasyExcelSelect;
import cn.easy.boot3.common.excel.converter.IntegerStatusToStringConvert;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "DataDict对象", description = "数据字典")
public class DataDict extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @Schema(title = "字典域ID")
    @TableField("domain_id")
    private Long domainId;

    @ExcelProperty(value = "字典键")
    @Schema(title = "字典键")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "字典标签")
    @Schema(title = "字典标签")
    @TableField("label")
    private String label;

    @EasyExcelSelect(code = "dict_status")
    @ExcelProperty(value = "字典状态", converter = IntegerStatusToStringConvert.class)
    @Schema(title = "字典状态 1：正常 2：禁用")
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
