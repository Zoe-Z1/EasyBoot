package com.fast.start.common.properties;

import com.fast.start.common.redis.RedisKeyConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zoe
 *
 * @describe 自定义日志组件配置
 * @date 2023/8/2
 */
@Component
@Builder
@ConfigurationProperties(prefix = "fast.lock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FastLock implements Serializable {

    private static final long serialVersionUID = -7434312891547319851L;

    /**
     * 锁key
     */
    private String key = RedisKeyConstant.FAST_REDIS_LOCK;

    /**
     * 锁持有时间/s
     */
    private Long leaseTime = 30L;

    /**
     * 获取锁等待时长/s
     */
    private Long waitTime = 5L;

}
