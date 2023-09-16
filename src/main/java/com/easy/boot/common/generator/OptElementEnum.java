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

        ;

    private String value;

    OptElementEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
