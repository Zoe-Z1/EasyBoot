package com.easy.boot.common.generator.db;

import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 表元数据信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaTable {

    /**
     * 表名称
     */
    private String name;

    /**
     * JavaBean名称
     */
    private String beanName;

    /**
     * Java驼峰名称
     */
    private String camelName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 表类型
     */
    private String type;

    /**
     * 表注释
     */
    private String remarks;

    /**
     * 表字段信息
     */
    private List<GenerateColumn> columns;

}
