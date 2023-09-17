package com.easy.boot.common.generator.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 表字段信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    /**
     * 是否主键
     */
    private Boolean isPrimaryKey;

    /**
     * 字段名
     */
    private String name;

    /**
     * Java命名
     */
    private String javaName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 转换后的Java类型
     */
    private String javaType;

    /**
     * 转换后的Java类型需要的包名
     */
    private String javaTypePackageName;

    /**
     * 字段是否允许为空 0：不为空 1：允许为空
     */
    private Integer nullable;

    /**
     * 字段注释
     */
    private String remarks;

    /**
     * 是否需要创建 #0：创建 1：不创建
     */
    private Integer isCreate;

    /**
     * 是否需要编辑 #0：编辑 1：不编辑
     */
    private Integer isUpdate;

    /**
     * 列表显示 #0：显示 1：不显示
     */
    private Integer listShow;

    /**
     * 详情显示 #0：显示 1：不显示
     */
    private Integer detailShow;

    /**
     * 是否导入 #0：导入 1：不导入
     */
    private Integer isImport;

    @ApiModelProperty("")
    private Integer isExport;

    @ApiModelProperty("是否必填 #0：必填 1：不必填")
    private Integer isRequired;

    @ApiModelProperty("操作组件 #input：文本框 textarea：文本域")
    private String optElement;

    @ApiModelProperty("字典域编码")
    private String dictDomainCode;
}
