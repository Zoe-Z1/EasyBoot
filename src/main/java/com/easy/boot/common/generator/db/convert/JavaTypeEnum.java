package com.easy.boot.common.generator.db.convert;

/**
 * @author zoe
 * @date 2023/8/19
 * @description Java数据类型枚举
 */
public enum JavaTypeEnum {

    STRING("String"),

    INTEGER("Integer"),

    LONG("Long"),

    SHORT("Short"),

    DOUBLE("Double"),

    BYTE("Byte"),

    BOOLEAN("Boolean"),

    FLOAT("Float"),

    CHAR("Character"),

    DATE("Date"),

    BIG_DECIMAL("BigDecimal"),

    TIMESTAMP("Timestamp"),

    TIME("Time"),

    BLOB("Blob"),

    ;

    private final String value;

    JavaTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
