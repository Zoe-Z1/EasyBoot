package cn.easy.boot3.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zoe
 * @date 2023/7/22
 * @description 线程池配置
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "LogThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor logThreadPoolTaskExecutor() {
        return this.threadPoolTaskExecutor("LogThreadPoolTaskExecutor");
    }

    private ThreadPoolTaskExecutor threadPoolTaskExecutor(String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置线程名称
        executor.setThreadNamePrefix(threadNamePrefix + " - ");
        //设置最大线程数
        executor.setMaxPoolSize(50);
        //设置核心线程数
        executor.setCorePoolSize(10);
        //设置线程空闲时间，默认60
        executor.setKeepAliveSeconds(60);
        //设置队列容量
        executor.setQueueCapacity(1000);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}
