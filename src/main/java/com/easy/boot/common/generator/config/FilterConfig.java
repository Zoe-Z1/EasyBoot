package com.easy.boot.common.generator.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zoe
 * @date 2023/8/29
 * @description 过滤配置
 */
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilterConfig {

    /**
     * 过滤表前缀
     * 生成代码时，类命名规则将去除该前缀
     */
    private Set<String> excludeTablePrefix;

    /**
     * 过滤表后缀
     * 生成代码时，类命名规则将去除该后缀
     */
    private Set<String> excludeTableSuffix;

    /**
     * 排除实体类属性
     */
    private Set<String> excludeField;

    public Set<String> getExcludeTablePrefix() {
        if (excludeTablePrefix == null) {
            excludeTablePrefix = new HashSet<>();
        }
        return excludeTablePrefix;
    }

    public Set<String> getExcludeTableSuffix() {
        if (excludeTableSuffix == null) {
            excludeTableSuffix = new HashSet<>();
        }
        return excludeTableSuffix;
    }

    public Set<String> getExcludeField() {
        if (excludeField == null) {
            excludeField = new HashSet<>();
        }
        return excludeField;
    }
}
