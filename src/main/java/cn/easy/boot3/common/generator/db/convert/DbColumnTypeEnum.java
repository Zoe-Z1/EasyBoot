package cn.easy.boot3.common.generator.db.convert;

import cn.easy.boot3.common.generator.OptElementEnum;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 数据库列类型枚举
 */
public enum DbColumnTypeEnum {

    /**
     * int
     */
    INT("INT", JavaTypeEnum.INTEGER, OptElementEnum.INPUT),

    BIGINT("BIGINT", JavaTypeEnum.LONG, OptElementEnum.INPUT),

    DECIMAL("DECIMAL", JavaTypeEnum.BIG_DECIMAL, OptElementEnum.INPUT),

    DATE("DATE", JavaTypeEnum.DATE, OptElementEnum.INPUT),

    TIME("TIME", JavaTypeEnum.TIME, OptElementEnum.INPUT),

    DATETIME("DATETIME", JavaTypeEnum.DATE, OptElementEnum.INPUT),

    TIMESTAMP("TIMESTAMP", JavaTypeEnum.TIMESTAMP, OptElementEnum.INPUT),

    VARCHAR("VARCHAR", JavaTypeEnum.STRING, OptElementEnum.INPUT),

    CHAR("CHAR", JavaTypeEnum.STRING, OptElementEnum.INPUT),

    BLOB("BLOB", JavaTypeEnum.BLOB, OptElementEnum.TEXTAREA),

    TEXT("TEXT", JavaTypeEnum.STRING, OptElementEnum.TEXTAREA),

    FLOAT("FLOAT", JavaTypeEnum.FLOAT, OptElementEnum.INPUT),

    ;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * Java类型
     */
    private JavaTypeEnum javaType;

    /**
     * 操作组件
     */
    private OptElementEnum optElement;

    DbColumnTypeEnum(String columnType, JavaTypeEnum javaType, OptElementEnum optElement) {
        this.columnType = columnType;
        this.javaType = javaType;
        this.optElement = optElement;
    }

    /**
     * 格式化
     * @param columnType 列名
     * @return
     */
    public static DbColumnTypeEnum format(String columnType) {
        DbColumnTypeEnum dbColumnType = null;
        try {
            dbColumnType = DbColumnTypeEnum.valueOf(columnType);
        } catch (Exception e) {
            dbColumnType = DbColumnTypeEnum.VARCHAR;
        }
        return dbColumnType;
    }

    /**
     * 转换为Java类型枚举
     * @param columnType 列名
     * @return JavaTypeEnum
     */
    public static JavaTypeEnum toJavaType(String columnType) {
        return format(columnType).javaType;
    }

    /**
     * 转换为操作组件
     * @param columnType 列名
     * @return OptElement
     */
    public static OptElementEnum toOptElement(String columnType) {
        return format(columnType).optElement;
    }
}
