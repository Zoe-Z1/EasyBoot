package com.easy.boot3.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zoe
 *
 * @describe 自定义日志组件配置
 * @date 2023/7/23
 */
@Component
@ConfigurationProperties(prefix = "easy.log")
@Data
public class EasyLog implements Serializable {

    private static final long serialVersionUID = -7434312891547219851L;

    /**
     * log日志开关,默认关闭
     */
    private Boolean enable = false;

    /**
     * 请求值最大保存长度，超过就丢弃
     */
    private Integer requestSaveMaxLength = 1000;

    /**
     * 返回值最大保存长度，超过就丢弃
     */
    private Integer responseSaveMaxLength = 1000;

    /**
     * 错误最大保存长度，超过就截断
     */
    private Integer errorSaveMaxLength = 1000;
}
