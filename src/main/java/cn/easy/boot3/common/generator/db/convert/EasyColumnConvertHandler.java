package cn.easy.boot3.common.generator.db.convert;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 字段类型转换实现
 */
public class EasyColumnConvertHandler implements ColumnConvertHandler {


    @Override
    public JavaTypeEnum convert(String columnType) {
        return DbColumnTypeEnum.toJavaType(columnType);
    }
}
