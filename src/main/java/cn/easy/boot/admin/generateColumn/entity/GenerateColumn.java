package cn.easy.boot.admin.generateColumn.entity;

import cn.easy.boot.common.base.BaseEntity;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @ApiModelProperty("Java类型 #String：String，Integer：Integer，Long：Long，Short：Short，Double：Double，Byte：Byte，" +
            "Boolean：Boolean，Float：Float，Character：Character，Date：Date，BigDecimal：BigDecimal")
    @TableField("java_type")
    @ExcelProperty(value = "Java类型")
    private String javaType;

    @ApiModelProperty("Java类型包名")
    @TableField("java_type_package_name")
    @ExcelProperty(value = "Java类型包名")
    private String javaTypePackageName;

    @ApiModelProperty("是否需要表单填写 #0：需要 1：不需要")
    @TableField("is_form")
    @ExcelProperty(value = "是否需要表单填写 #0：需要 1：不需要")
    private Integer isForm;

    @ApiModelProperty("是否需要关键词搜索 #0：需要 1：不需要")
    @TableField("is_keyword")
    @ExcelProperty(value = "是否需要关键词搜索 #0：需要 1：不需要")
    private Integer isKeyword;

    @ApiModelProperty("是否需要高级搜索 #0：需要 1：不需要")
    @TableField("is_advanced_search")
    @ExcelProperty(value = "是否需要高级搜索 #0：需要 1：不需要")
    private Integer isAdvancedSearch;

    @ApiModelProperty("列表显示 #0：显示 1：不显示")
    @TableField("list_show")
    @ExcelProperty(value = "列表显示 #0：显示 1：不显示")
    private Integer listShow;

    @ApiModelProperty("详情显示 #0：显示 1：不显示")
    @TableField("detail_show")
    @ExcelProperty(value = "详情显示 #0：显示 1：不显示")
    private Integer detailShow;

    @ApiModelProperty("是否需要导入导出 #0：需要 1：不需要")
    @TableField("is_excel")
    @ExcelProperty(value = "是否需要导入导出 #0：需要 1：不需要")
    private Integer isExcel;

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

    @ExcelIgnore
    @TableField(exist = false)
    @ApiModelProperty("是否需要创建")
    private Boolean isCreate;

    public Boolean getIsCreate() {
        if (isCreate == null) {
            isCreate = true;
        }
        return isCreate;
    }

}
