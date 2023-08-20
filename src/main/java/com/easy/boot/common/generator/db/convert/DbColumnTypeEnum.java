package com.easy.boot.common.generator.db.convert;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 数据库列类型枚举
 */
public enum DbColumnTypeEnum {

    BIGINT("BIGINT", JavaTypeEnum.LONG),

    DECIMAL("DECIMAL", JavaTypeEnum.BIG_DECIMAL),

    DATE("DATE", JavaTypeEnum.DATE),

    TIME("TIME", JavaTypeEnum.TIME),

    DATETIME("DATETIME", JavaTypeEnum.DATE),

    TIMESTAMP("TIMESTAMP", JavaTypeEnum.TIMESTAMP),

    VARCHAR("VARCHAR", JavaTypeEnum.STRING),

    CHAR("CHAR", JavaTypeEnum.STRING),

    BLOB("BLOB", JavaTypeEnum.BLOB),

    TEXT("TEXT", JavaTypeEnum.STRING),

    FLOAT("FLOAT", JavaTypeEnum.FLOAT),

    ;


    private String columnType;

    private JavaTypeEnum javaType;

    DbColumnTypeEnum(String columnType, JavaTypeEnum javaType) {
        this.columnType = columnType;
        this.javaType = javaType;
    }

    /**
     * 转换为Java类型枚举
     * @return JavaTypeEnum
     */
    public static JavaTypeEnum toJavaType(String columnType) {
        DbColumnTypeEnum dbColumnType = null;
        try {
            dbColumnType = DbColumnTypeEnum.valueOf(columnType);
        } catch (Exception e) {
            dbColumnType = DbColumnTypeEnum.VARCHAR;
        }
        return dbColumnType.javaType;
    }

    /**
     * 转换为Java类型
     * @return javaType
     */
    public static String toJavaTypeValue(String columnType) {
        return toJavaType(columnType).getValue();
    }
}
