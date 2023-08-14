package com.easy.boot.common.quartz.manager;

import com.easy.boot.admin.scheduledTask.entity.ScheduledTask;
import com.easy.boot.common.quartz.EasyJobTaskInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zoe
 * 
 * @describe quartz 定时任务执行操作实现类
 * @date 2023/8/5
 */
@Slf4j
@Component
public class Test2TaskManager implements EasyJobTaskInterface {

    @Override
    public String getKey() {
        return "test2";
    }

    /**
     * 执行定时任务
     *
     * @param task 任务参数
     */
    @Override
    public void execute(ScheduledTask task) {
        log.info("进入定时任务2，task： {} ", task);
    }
}
