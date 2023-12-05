package cn.easy.boot.common.generator.db;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 表名匹配模式枚举
 */
public enum MatchPatternEnum {

    /**
     * 精确匹配
     */
    EQUALS("%s"),

    /**
     * 左模糊匹配
     */
    LEFT_LIKE("%%%s"),

    /**
     * 右模糊匹配
     */
    RIGHT_LIKE("%s%%'"),

    /**
     * 模糊匹配
     */
    ALL_LIKE("%%%s%%"),

    ;

    /**
     * 格式化规则
     */
    private String format;

    MatchPatternEnum(String format) {
        this.format = format;
    }

    /**
     * 格式化表名
     * @param tableName 表名
     * @return tableName
     */
    public String formatTableName(String tableName) {
        return String.format(format, tableName);
    }
}
