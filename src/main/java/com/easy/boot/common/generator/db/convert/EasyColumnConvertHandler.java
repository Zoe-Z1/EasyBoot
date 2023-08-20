package com.easy.boot.common.generator.db.convert;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 字段类型转换实现
 */
public class EasyColumnConvertHandler implements ColumnConvertHandler {


    @Override
    public String convert(String columnType) {
        return DbColumnTypeEnum.toJavaTypeValue(columnType);
    }
}
