package com.easy.boot.common.generator.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 注解配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationConfig {

    /**
     * 开启log注解
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
