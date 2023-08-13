package com.fast.start.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zoe
 *
 * @describe 自定义日志字段过滤配置
 * @date 2023/8/10
 */
@Component
@ConfigurationProperties(prefix = "fast.log.filter")
@Data
public class FastLogFilter implements Serializable {

    private static final long serialVersionUID = -7434312894547219851L;

    /**
     * 保存日志时要过滤的字段名
     */
    private String[] fields = new String[]{"password", "token"};
}
