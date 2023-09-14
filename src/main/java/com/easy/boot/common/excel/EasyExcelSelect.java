package com.easy.boot.common.excel;

import com.easy.boot.common.excel.enums.ExcelSelectDataLoadTypeEnum;

import java.lang.annotation.*;

/**
 * @author zoe
 * @describe 自定义EasyExcel下拉转换注解
 * @date 2023/9/13
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyExcelSelect {

    // 下拉框数据加载方式
    ExcelSelectDataLoadTypeEnum loadType() default ExcelSelectDataLoadTypeEnum.QUERY;

    // 数据字典域编码，loadType为 QUERY 时必填
    String code() default "";

    // 下拉框预定义数据数组，loadType为 PREDEFINE 时必填
    String[] values() default {};
}
