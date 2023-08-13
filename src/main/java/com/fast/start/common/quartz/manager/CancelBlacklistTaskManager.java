package com.fast.start.common.quartz.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.fast.start.admin.blacklist.entity.Blacklist;
import com.fast.start.admin.blacklist.service.IBlacklistService;
import com.fast.start.admin.scheduledTask.entity.ScheduledTask;
import com.fast.start.common.quartz.FastJobTaskInterface;
import com.fast.start.common.redis.FastRedisManager;
import com.fast.start.common.redis.RedisKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zoe
 * 
 * @describe quartz 取消拉黑定时任务
 * @date 2023/8/5
 */
@Slf4j
@Component
public class CancelBlacklistTaskManager implements FastJobTaskInterface {

    @Resource
    private IBlacklistService blacklistService;

    @Resource
    private FastRedisManager fastRedisManager;

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
        List<Blacklist> list = blacklistService.selectNotForeverList();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<Long> cancelIds = new ArrayList<>();
        Iterator<Blacklist> iterator = list.iterator();
        while (iterator.hasNext()) {
            Blacklist blacklist = iterator.next();
            // 计算拉黑结束时间 小于等于当前时间则代表拉黑结束
            long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
            if (endTime <= DateUtil.current()) {
                cancelIds.add(blacklist.getId());
                iterator.remove();
            }
        }
        // 从黑名单中删除
        blacklistService.deleteBatchByIds(cancelIds);
        // 剩下的重新加入缓存
        String key = RedisKeyEnum.NOT_FOREVER_BLACKLIST.getKey();
        fastRedisManager.put(key, fastRedisManager);
    }
}
