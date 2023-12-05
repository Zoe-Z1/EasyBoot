package cn.easy.boot3;

import cn.easy.boot3.common.redis.EasyRedisManager;
import cn.easy.boot3.common.redis.RedisKeyConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zoe
 * @describe 配置springboot启动完成后
 *  自动打开浏览器进入接口文档页面
 * @date 2023/7/23
 */
@Slf4j
@Component
public class EasyCommandRunner implements CommandLineRunner {

    @Resource
    private EasyRedisManager easyRedisManager;

    @Override
    public void run(String... args) throws Exception {
        easyRedisManager.put(RedisKeyConstant.PROJECT_START_TIME, System.currentTimeMillis());
    }
}
