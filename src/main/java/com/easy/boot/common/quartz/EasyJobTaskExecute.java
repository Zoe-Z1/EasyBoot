package com.easy.boot.common.quartz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.easy.boot.admin.operationLog.enums.OperateStatusEnum;
import com.easy.boot.admin.scheduledTask.entity.ScheduledTask;
import com.easy.boot.admin.taskLog.entity.TaskLog;
import com.easy.boot.admin.taskLog.service.ITaskLogService;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zoe
 * @describe 执行定时任务
 * @date 2023/8/4
 */
@Slf4j
public class EasyJobTaskExecute extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        var easyTimingJobTaskInterfaces = SpringUtil.getBeansOfType(EasyJobTaskInterface.class);
        if (CollUtil.isEmpty(easyTimingJobTaskInterfaces)) {
            return;
        }
        // 获取参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Object object = jobDataMap.get(EasyJobManager.JOB_DATA_MAP_KEY);
        String jsonStr = JsonUtil.toJsonStr(object);
        ScheduledTask task = JsonUtil.toBean(jsonStr, ScheduledTask.class);

        log.info("进入定时任务，任务参数为 ：{} ，当前时间为 ：{}", task, DateUtil.now());
        List<TaskLog> taskLogs = new ArrayList<>();
        for (String mapKey : easyTimingJobTaskInterfaces.keySet()) {
            TaskLog taskLog = null;
            try {
                String key = easyTimingJobTaskInterfaces.get(mapKey).getKey();
                if (StrUtil.isEmpty(key)) {
                    continue;
                }
                if (task.getJobKey().equals(key)) {
                    taskLog = BeanUtil.copyBean(task, TaskLog.class);
                    taskLog.setTaskId(task.getId());
                    taskLog.setStartTime(DateUtil.current());
                    taskLog.setId(null);

                    // 执行任务
                    easyTimingJobTaskInterfaces.get(mapKey).execute(task);

                    taskLog.setStatus(String.valueOf(OperateStatusEnum.SUCCESS));
                }
            } catch (Exception e) {
                log.error("执行定时任务出错，时间 ：{}", DateUtil.now(), e);
                if (taskLog != null) {
                    taskLog.setStatus(String.valueOf(OperateStatusEnum.FAIL));
                }
            } finally {
                if (taskLog != null) {
                    taskLog.setEndTime(DateUtil.current());
                    taskLog.setHandleTime(taskLog.getEndTime() - taskLog.getStartTime());
                    taskLog.setCreateTime(DateUtil.current());
                    taskLog.setUpdateTime(DateUtil.current());
                    taskLogs.add(taskLog);
                }
            }
        }
        ITaskLogService taskLogService = SpringUtil.getBean(ITaskLogService.class);
        taskLogService.asyncBatchSave(taskLogs);
    }
}
