package com.easy.boot.common.sensitive;

import java.util.function.Function;

/**
 * @author zoe
 * @date 2023/7/29
 * @description 脱敏类型枚举
 */
public enum EasySensitiveStrategyEnum {

    /**
     * 置空
     */
    NONE(s -> null),
    /**
     * 昵称
     */
    NAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),
    /**
     * 身份证
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),
    /**
     * 手机号
     */
    MOBILE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    /**
     * 地址
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"));

    private final Function<String, String> desensitizer;

    EasySensitiveStrategyEnum(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    /**
     * 脱敏器
     * @return
     */
    public Function<String, String> desensitizer() {
        return desensitizer;
    }

}
