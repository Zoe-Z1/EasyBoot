package com.easy.boot3.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zoe
 *
 * @describe 自定义防重复提交组件配置
 * @date 2023/7/22
 */
@ConfigurationProperties(prefix = "easy.no-repeat-submit")
@Data
@Component
public class EasyNoRepeatSubmit implements Serializable {

    private static final long serialVersionUID = 4078145761972526585L;

    /**
     * 全局防重复提交开关,默认关闭
     */
    private Boolean enable = false;

    /**
     * 全局重复判定时间,默认1000ms
     */
    private Long timeout = 1000L;

}
