package com.easy.boot3.common.generator.db.convert;

import com.easy.boot3.common.generator.OptElementEnum;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 字段类型转换实现
 */
public class EasyOptElementConvertHandler implements OptElementConvertHandler {


    @Override
    public OptElementEnum convert(String columnType) {
        return DbColumnTypeEnum.toOptElement(columnType);
    }
}
