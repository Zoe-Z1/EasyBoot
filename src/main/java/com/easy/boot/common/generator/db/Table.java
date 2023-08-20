package com.easy.boot.common.generator.db;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.exception.GeneratorException;
import lombok.*;

/**
 * @author zoe
 * @date 2023/8/19
 * @description
 */
@Builder
@Setter
@AllArgsConstructor
public class Table {

    /**
     * 需要生成的表名称
     */
    private String tableName;

    /**
     * 表对应的模块名称，可不填，不填不按模块生成
     */
    private String moduleName;

    /**
     * 表名匹配模式，默认精确匹配
     */
    private MatchPatternEnum matchPattern = MatchPatternEnum.EQUALS;

    /**
     * 获取表名
     * @return tableName
     */
    public String getTableName() {
        if (StrUtil.isEmpty(tableName)) {
            throw new GeneratorException("表名不能为空");
        }
        if (matchPattern == null) {
            throw new GeneratorException("表名匹配模式不能为空");
        }
        return matchPattern.formatTableName(tableName);
    }

    public String getModuleName() {
        return moduleName;
    }

    public MatchPatternEnum getMatchPattern() {
        return matchPattern;
    }

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public Table(String tableName, MatchPatternEnum matchPattern) {
        this.tableName = tableName;
        this.matchPattern = matchPattern;
    }
}
