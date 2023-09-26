package com.easy.boot.admin.generateColumn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;


/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置编辑实体
 */
@TableName("generate_column")
@ApiModel(value = "代码生成列配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateColumnUpdateDTO {


    @NotEmpty(message = "表名称不能为空")
    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("是否主键  #0：是 1：不是")
    private Integer isPrimaryKey;

    @ApiModelProperty("列名")
    private String columnName;

    @ApiModelProperty("列类型")
    private String columnType;

    @ApiModelProperty("列描述")
    private String columnRemarks;

    @ApiModelProperty("是否必填 #0：必填，1：非必填")
    private Integer nullable;

    @ApiModelProperty("Java命名")
    private String javaName;

    @ApiModelProperty("Java类型")
    private String javaType;

    @ApiModelProperty("是否需要表单填写 #0：需要，1：不需要")
    private Integer isForm;

    @ApiModelProperty("是否需要关键词搜索 #0：需要 1：不需要")
    private Integer isKeyword;

    @ApiModelProperty("是否需要高级搜索 #0：需要，1：不需要")
    private Integer isAdvancedSearch;

    @ApiModelProperty("列表显示 #0：显示，1：不显示")
    private Integer listShow;

    @ApiModelProperty("详情显示 #0：显示，1：不显示")
    private Integer detailShow;

    @ApiModelProperty("是否需要导入导出 #0：需要，1：不需要")
    private Integer isExcel;

    @ApiModelProperty("是否导出 #0：导出，1不导出")
    private Integer isExport;

    @ApiModelProperty("是否必填 #0：必填，1：不必填")
    private Integer isRequired;

    @ApiModelProperty("操作组件 #input：文本框，textarea：文本域")
    private String optElement;

    @ApiModelProperty("字典域编码")
    private String dictDomainCode;

}
