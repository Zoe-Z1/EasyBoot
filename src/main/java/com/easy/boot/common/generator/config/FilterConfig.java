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
    private Set<String> tablePrefix;

    /**
     * 过滤表后缀
     * 生成代码时，类命名规则将去除该后缀
     */
    private Set<String> tableSuffix;

    public Set<String> getTablePrefix() {
        if (tablePrefix == null) {
            tablePrefix = new HashSet<>();
        }
        return tablePrefix;
    }

    public Set<String> getTableSuffix() {
        if (tableSuffix == null) {
            tableSuffix = new HashSet<>();
        }
        return tableSuffix;
    }

    public static FilterConfig.FilterConfigBuilder builder() {
        return new FilterConfig.FilterConfigBuilder();
    }

    @ToString
    public static class FilterConfigBuilder {
        private Set<String> tablePrefix;
        private Set<String> tableSuffix;

        FilterConfigBuilder() {
        }

        public FilterConfig.FilterConfigBuilder addTablePrefix(String... tablePrefix) {
            this.tablePrefix = CollUtil.newHashSet(tablePrefix);
            return this;
        }

        public FilterConfig.FilterConfigBuilder addTableSuffix(String... tableSuffix) {
            this.tableSuffix = CollUtil.newHashSet(tableSuffix);
            return this;
        }

        public FilterConfig build() {
            return new FilterConfig(this.tablePrefix, this.tableSuffix);
        }
    }
}
