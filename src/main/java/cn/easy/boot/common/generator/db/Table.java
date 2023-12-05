package cn.easy.boot.common.generator.db;

import cn.hutool.core.util.StrUtil;
import cn.easy.boot.exception.GeneratorException;
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
     * 表备注
     */
    private String remarks;

    /**
     * 表对应的模块名称，可不填，不填则使用驼峰命名
     */
    private String moduleName;

    /**
     * 表名匹配模式，默认精确匹配
     */
    private MatchPatternEnum matchPattern;

    public String getTableName() {
        if (StrUtil.isEmpty(tableName)) {
            throw new GeneratorException("表名不能为空");
        }
        return getMatchPattern().formatTableName(tableName);
    }

    public String getRemarks() {
        return remarks;
    }

    public String getModuleName() {
        return moduleName;
    }

    public MatchPatternEnum getMatchPattern() {
        if (matchPattern == null) {
            matchPattern = MatchPatternEnum.EQUALS;
        }
        return matchPattern;
    }

    public Table(String tableName) {
        this.tableName = tableName;
    }
}
