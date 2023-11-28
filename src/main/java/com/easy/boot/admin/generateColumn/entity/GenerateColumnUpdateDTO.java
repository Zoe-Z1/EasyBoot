package com.easy.boot.admin.generateColumn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotEmpty;


/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置编辑实体
 */
@TableName("generate_column")
@Schema(title = "代码生成列配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateColumnUpdateDTO {


    @NotEmpty(message = "表名称不能为空")
    @Schema(title = "表名称")
    private String tableName;

    @Schema(title = "是否主键  #0：是 1：不是")
    private Integer isPrimaryKey;

    @Schema(title = "列名")
    private String columnName;

    @Schema(title = "列类型")
    private String columnType;

    @Schema(title = "列描述")
    private String columnRemarks;

    @Schema(title = "是否必填 #0：必填，1：非必填")
    private Integer nullable;

    @Schema(title = "Java命名")
    private String javaName;

    @Schema(title = "Java类型")
    private String javaType;

    @Schema(title = "是否需要表单填写 #0：需要，1：不需要")
    private Integer isForm;

    @Schema(title = "是否需要关键词搜索 #0：需要 1：不需要")
    private Integer isKeyword;

    @Schema(title = "是否需要高级搜索 #0：需要，1：不需要")
    private Integer isAdvancedSearch;

    @Schema(title = "列表显示 #0：显示，1：不显示")
    private Integer listShow;

    @Schema(title = "详情显示 #0：显示，1：不显示")
    private Integer detailShow;

    @Schema(title = "是否需要导入导出 #0：需要，1：不需要")
    private Integer isExcel;

    @Schema(title = "是否导出 #0：导出，1不导出")
    private Integer isExport;

    @Schema(title = "是否必填 #0：必填，1：不必填")
    private Integer isRequired;

    @Schema(title = "操作组件 #input：文本框，textarea：文本域")
    private String optElement;

    @Schema(title = "字典域编码")
    private String dictDomainCode;

}
