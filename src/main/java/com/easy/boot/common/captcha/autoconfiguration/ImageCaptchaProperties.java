package com.easy.boot.common.captcha.autoconfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Collections;
import java.util.Map;

/**
 * @Author: 天爱有情
 * @date 2020/10/19 18:41
 * @Description 滑块验证码属性
 */
@Data
@ConfigurationProperties(prefix = "captcha")
public class ImageCaptchaProperties {
    /** 过期key prefix. */
    private String prefix = "captcha";
    /** 过期时间. */
    private Map<String, Long> expire = Collections.emptyMap();
    /** 是否初始化默认资源. */
    private Boolean initDefaultResource = false;
    /** 二次验证配置. */
    @NestedConfigurationProperty
    private SecondaryVerificationProperties secondary;
    /** 缓存配置. */
    @NestedConfigurationProperty
    private SliderCaptchaCacheProperties cache;

}
