package com.easy.boot.admin.dataDict.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDict对象", description = "数据字典")
public class DataDictExcelDO extends DataDict {

    @ColumnWidth(20)
    @ExcelProperty(value = "字典域编码")
    @ApiModelProperty("字典域编码")
    private String domainCode;

}
