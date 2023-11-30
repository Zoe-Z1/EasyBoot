package com.easy.boot3.common.generator.db.convert;

import com.easy.boot3.common.generator.OptElementEnum;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 字段类型转换处理器
 */
public interface OptElementConvertHandler {

    /**
     * 获取默认转换器
     * @return EasyOptElementConvertHandler
     */
    static OptElementConvertHandler defaultHandler() {
        return new EasyOptElementConvertHandler();
    }

    /**
     * 将表字段类型转换为操作组件
     * @param columnType 数据库表列类型
     * @return optElement
     */
    OptElementEnum convert(String columnType);
}
