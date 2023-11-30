package com.easy.boot3.common.redisson;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zoe
 * @date 2023/8/3
 * @description redisson连接配置类
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private Integer port;

    @Value("${spring.data.redis.database}")
    private Integer database;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        String address = "redis://" + host + ":" + port;
        Config config = new Config();
        config.useSingleServer()
                .setDatabase(database)
                .setAddress(address);
        if (StrUtil.isNotEmpty(password)) {
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }
}
