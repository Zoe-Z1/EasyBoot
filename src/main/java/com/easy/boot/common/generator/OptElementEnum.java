package com.easy.boot.common.generator;

/**
 * @author zoe
 * @date 2023/9/16
 * @description 操作组件枚举
 */
public enum OptElementEnum {

    /**
     * 文本框
     */
    INPUT("input"),
    /**
     * 文本域
     */
    TEXTAREA("textarea"),
    /**
     * 下拉框
     */
    SELECT("select"),
    /**
     * 单选框
     */
    RADIO("radio"),
    /**
     * 多选框
     */
    CHECKBOX("checkbox"),
    /**
     * 时间选择器
     */
    TIMEPICKER("timepicker"),
    /**
     * 日期选择器
     */
    DATEPICKER("datepicker"),
    /**
     * 日期时间选择器
     */
    DATETIMEPICKER("datetimepicker"),
    /**
     * 计数器
     */
    INPUTNUMBER("inputnumber"),

        ;

    private String value;

    OptElementEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
