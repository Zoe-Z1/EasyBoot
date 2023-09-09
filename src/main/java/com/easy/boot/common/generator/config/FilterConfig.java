package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zoe
 * @date 2023/8/29
 * @description 过滤配置
 */
@Setter
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

    public static FilterConfig.FilterConfigBuilder builder() {
        return new FilterConfig.FilterConfigBuilder();
    }

    @ToString
    public static class FilterConfigBuilder {
        private Set<String> excludeTablePrefix;
        private Set<String> excludeTableSuffix;

        FilterConfigBuilder() {
        }

        public FilterConfig.FilterConfigBuilder addExcludeTablePrefix(String... excludeTablePrefix) {
            this.excludeTablePrefix = CollUtil.newHashSet(excludeTablePrefix);
            return this;
        }

        public FilterConfig.FilterConfigBuilder addExcludeTableSuffix(String... excludeTableSuffix) {
            this.excludeTableSuffix = CollUtil.newHashSet(excludeTableSuffix);
            return this;
        }

        public FilterConfig build() {
            return new FilterConfig(this.excludeTablePrefix, this.excludeTableSuffix);
        }
    }
}
