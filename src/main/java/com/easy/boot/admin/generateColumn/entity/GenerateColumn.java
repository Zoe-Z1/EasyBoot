package com.easy.boot.admin.generateColumn.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置实体
 */
@TableName("generate_column")
@ApiModel(value = "代码生成列配置实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenerateColumn extends BaseEntity {


    @ApiModelProperty("表名称")
    @TableField("table_name")
    @ExcelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty("是否主键  #0：是 1：不是")
    @TableField("is_primary_key")
    @ExcelProperty(value = "是否主键  #0：是 1：不是")
    private Integer isPrimaryKey;

    @ApiModelProperty("列名")
    @TableField("column_name")
    @ExcelProperty(value = "列名")
    private String columnName;

    @ApiModelProperty("列类型")
    @TableField("column_type")
    @ExcelProperty(value = "列类型")
    private String columnType;

    @ApiModelProperty("列描述")
    @TableField("column_remarks")
    @ExcelProperty(value = "列描述")
    private String columnRemarks;

    @ApiModelProperty("是否必填 #0：必填 1：非必填")
    @TableField("nullable")
    @ExcelProperty(value = "是否必填 #0：必填 1：非必填")
    private Integer nullable;

    @ApiModelProperty("Java命名")
    @TableField("java_name")
    @ExcelProperty(value = "Java命名")
    private String javaName;

    @ApiModelProperty("Java类型")
    @TableField("java_type")
    @ExcelProperty(value = "Java类型")
    private String javaType;

    @ApiModelProperty("Java类型包名")
    @TableField("java_type_package_name")
    @ExcelProperty(value = "Java类型包名")
    private String javaTypePackageName;

    @ApiModelProperty("是否需要创建 #0：创建 1：不创建")
    @TableField("is_create")
    @ExcelProperty(value = "是否需要创建 #0：创建 1：不创建")
    private Integer isCreate;

    @ApiModelProperty("是否需要编辑 #0：编辑 1：不编辑")
    @TableField("is_update")
    @ExcelProperty(value = "是否需要编辑 #0：编辑 1：不编辑")
    private Integer isUpdate;

    @ApiModelProperty("列表显示 #0：显示 1：不显示")
    @TableField("list_show")
    @ExcelProperty(value = "列表显示 #0：显示 1：不显示")
    private Integer listShow;

    @ApiModelProperty("详情显示 #0：显示 1：不显示")
    @TableField("detail_show")
    @ExcelProperty(value = "详情显示 #0：显示 1：不显示")
    private Integer detailShow;

    @ApiModelProperty("是否导入 #0：导入 1：不导入")
    @TableField("is_import")
    @ExcelProperty(value = "是否导入 #0：导入 1：不导入")
    private Integer isImport;

    @ApiModelProperty("是否导出 #0：导出 1：不导出")
    @TableField("is_export")
    @ExcelProperty(value = "是否导出 #0：导出 1：不导出")
    private Integer isExport;

    @ApiModelProperty("是否必填 #0：必填 1：不必填")
    @TableField("is_required")
    @ExcelProperty(value = "是否必填 #0：必填 1：不必填")
    private Integer isRequired;

    @ApiModelProperty("操作组件 #input：文本框 textarea：文本域")
    @TableField("opt_element")
    @ExcelProperty(value = "操作组件 #input：文本框 textarea：文本域")
    private String optElement;

    @ApiModelProperty("字典域编码")
    @TableField("dict_domain_code")
    @ExcelProperty(value = "字典域编码")
    private String dictDomainCode;
}
