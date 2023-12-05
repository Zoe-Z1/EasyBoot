package cn.easy.boot.common.quartz.manager;

import cn.easy.boot.admin.blacklist.service.IBlacklistService;
import cn.easy.boot.admin.scheduledTask.entity.ScheduledTask;
import cn.easy.boot.common.quartz.EasyJobTaskInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zoe
 * 
 * @describe quartz 取消拉黑定时任务
 * @date 2023/8/5
 */
@Slf4j
@Component
public class CancelBlacklistTaskManager implements EasyJobTaskInterface {

    @Resource
    private IBlacklistService blacklistService;


    @Override
    public String getKey() {
        return "CancelBlacklist";
    }

    /**
     * 执行定时任务
     *
     * @param task 任务参数
     */
    @Override
    public void execute(ScheduledTask task) {
        log.info("取消拉黑定时任务，task： {} ", task);
        blacklistService.updateBlacklistStatus();
    }
}
