package cn.easy.boot.common.generator.db.convert;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author zoe
 * @date 2023/8/19
 * @description Java数据类型枚举
 */
public enum JavaTypeEnum {

    STRING("String", String.class.getName()),

    INTEGER("Integer", Integer.class.getName()),

    LONG("Long", Long.class.getName()),

    SHORT("Short", Short.class.getName()),

    DOUBLE("Double", Double.class.getName()),

    BYTE("Byte", Byte.class.getName()),

    BOOLEAN("Boolean", Boolean.class.getName()),

    FLOAT("Float", Float.class.getName()),

    CHAR("Character", Character.class.getName()),

    DATE("Date", Date.class.getName()),

    BIG_DECIMAL("BigDecimal", BigDecimal.class.getName()),

    TIMESTAMP("Timestamp", Timestamp.class.getName()),

    TIME("Time", Time.class.getName()),

    BLOB("Blob", Blob.class.getName()),

    ;

    private String value;

    private String packageName;

    JavaTypeEnum(String value, String packageName) {
        this.value = value;
        this.packageName = packageName;
    }

    /**
     * 获取类型
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取包名
     * @return
     */
    public String getPackageName() {
        return packageName;
    }
}
