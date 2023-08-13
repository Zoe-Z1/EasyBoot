package com.fast.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @description Springboot启动类
 * @author zoe
 * @date 2023/7/21
 */
@EnableAsync
@SpringBootApplication
public class FastStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastStartApplication.class, args);
    }

}
