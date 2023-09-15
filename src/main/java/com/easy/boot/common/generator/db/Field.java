package com.easy.boot.common.generator.db;

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
     * 字段长度
     */
    private Integer size;

    /**
     * 字段是否允许为空 0：不为空 1：允许为空
     */
    private Integer nullable;

    /**
     * 字段注释
     */
    private String remarks;
}
