package com.easy.boot.common.quartz.manager;

import com.easy.boot.admin.onlineUser.service.IOnlineUserService;
import com.easy.boot.admin.scheduledTask.entity.ScheduledTask;
import com.easy.boot.common.quartz.EasyJobTaskInterface;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zoe
 * 
 * @describe quartz 更新用户在线状态定时任务
 * @date 2023/10/25
 */
@Slf4j
@Component
public class UpdateIsOnlineTaskManager implements EasyJobTaskInterface {

    @Resource
    private IOnlineUserService onlineUserService;

    @Override
    public String getKey() {
        return "UpdateIsOnline";
    }

    /**
     * 执行定时任务
     *
     * @param task 任务参数
     */
    @Override
    public void execute(ScheduledTask task) {
        log.info("更新用户在线状态定时任务，task： {} ", task);
        onlineUserService.updateIsOnline();
    }
}
