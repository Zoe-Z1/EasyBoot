package com.easy.boot.admin.generateColumn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置创建实体
 */
@TableName("generate_column")
@ApiModel(value = "代码生成列配置创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class GenerateColumnCreateDTO {


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

    @ApiModelProperty("是否必填 #0：必填 1：非必填")
    private Integer nullable;

    @ApiModelProperty("Java命名")
    private String javaName;

    @ApiModelProperty("Java类型")
    private String javaType;

    @ApiModelProperty("Java类型包名")
    private String javaTypePackageName;

    @ApiModelProperty("是否需要创建 #0：创建 1：不创建")
    private Integer isCreate;

    @ApiModelProperty("是否需要编辑 #0：编辑 1：不编辑")
    private Integer isUpdate;

    @ApiModelProperty("列表显示 #0：显示 1：不显示")
    private Integer listShow;

    @ApiModelProperty("详情显示 #0：显示 1：不显示")
    private Integer detailShow;

    @ApiModelProperty("是否导入 #0：导入 1：不导入")
    private Integer isImport;

    @ApiModelProperty("是否导出 #0：导出 1：不导出")
    private Integer isExport;

    @ApiModelProperty("是否必填 #0：必填 1：不必填")
    private Integer isRequired;

    @ApiModelProperty("操作组件 #input：文本框 textarea：文本域")
    private String optElement;

    @ApiModelProperty("字典域编码")
    private String dictDomainCode;

    @ApiModelProperty("创建者账号")
    private String createUsername;

    @ApiModelProperty("更新者账号")
    private String updateUsername;
}
