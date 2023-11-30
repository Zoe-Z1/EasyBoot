package com.easy.boot3.common.quartz;


import com.easy.boot3.admin.scheduledTask.entity.ScheduledTask;

/**
 * @author zoe
 * @describe quartz 定时任务执行操作接口
 * 多个不同的任务则创建多个实现类实现该接口即可
 * @date 2023/8/4
 */
public interface EasyJobTaskInterface {

    /**
     * 任务key - 配置定时任务时使用，根据key识别执行哪个实现类
     * @return key
     */
    String getKey();

    /**
     * 执行定时任务
     * @param task 任务参数
     */
    void execute(ScheduledTask task);
}
