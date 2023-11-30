package com.easy.boot3.common.generator.config;

import lombok.*;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 注解配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationConfig {

    /**
     * 生成log注解
     */
    private Boolean enableLog;

    /**
     * 开启builder模式
     */
    private Boolean enableBuilder;

    public Boolean getEnableLog() {
        if (enableLog == null) {
            enableLog = true;
        }
        return enableLog;
    }

    public Boolean getEnableBuilder() {
        if (enableBuilder == null) {
            enableBuilder = true;
        }
        return enableBuilder;
    }
}
