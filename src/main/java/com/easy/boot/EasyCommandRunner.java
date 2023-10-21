package com.easy.boot;

import com.easy.boot.common.redis.EasyRedisManager;
import com.easy.boot.common.redis.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zoe
 * @describe 配置springboot启动完成后
 *  自动打开浏览器进入接口文档页面
 * @date 2023/7/23
 */
@Slf4j
@Component
public class EasyCommandRunner implements CommandLineRunner {

    @Value("${knife4j.enable}")
    private Boolean enable;

    @Value("${server.port}")
    private String port;

    @Resource
    private EasyRedisManager easyRedisManager;

    @Override
    public void run(String... args) throws Exception {
        easyRedisManager.put(RedisKeyConstant.PROJECT_START_TIME, System.currentTimeMillis());
        if (enable){
            String os = System.getProperty("os.name");
            String path = "http://localhost:" + port + "/doc.html";

            if (os.toLowerCase().contains("mac")) {
                log.info("knife4j addr --->>  " + path);
//                Runtime.getRuntime().exec("open " + path);
            }
            if (os.toLowerCase().contains("win")) {
                log.info("knife4j addr --->>  " + path);
//                Runtime.getRuntime().exec("cmd   /c   start   " + path);
            }
        }
    }
}
