package cn.easy.boot.common.generator.db.convert;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 字段类型转换处理器
 */
public interface ColumnConvertHandler {

    /**
     * 获取默认转换器
     * @return EasyColumnConvertHandler
     */
    static ColumnConvertHandler defaultHandler() {
        return new EasyColumnConvertHandler();
    }

    /**
     * 将表字段类型转换为Java类型
     * @param columnType 数据库表列类型
     * @return javaType
     */
    JavaTypeEnum convert(String columnType);
}
