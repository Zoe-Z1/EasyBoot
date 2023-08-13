package com.fast.start.common.quartz.manager;

import com.fast.start.admin.scheduledTask.entity.ScheduledTask;
import com.fast.start.common.quartz.FastJobTaskInterface;
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
public class Test1TaskManager implements FastJobTaskInterface {

    @Override
    public String getKey() {
        return "test1";
    }

    /**
     * 执行定时任务
     *
     * @param task 任务参数
     */
    @Override
    public void execute(ScheduledTask task) {
        log.info("进入定时任务1，task： {} ", task);
    }
}
