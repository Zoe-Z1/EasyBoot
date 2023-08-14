package com.easy.boot.common.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.boot.admin.scheduledTask.entity.ScheduledTask;
import com.easy.boot.admin.scheduledTask.entity.StartNowJobDTO;
import com.easy.boot.admin.scheduledTask.mapper.ScheduledTaskMapper;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zoe
 * @describe quartz 定时任务动态操作实现类
 * @date 2023/8/5
 */
@Slf4j
@Component
public class EasyJobManager {

    @Resource
    private Scheduler scheduler;

    @Resource
    private ScheduledTaskMapper scheduledTaskMapper;

    /**
     * jobDataMap参数的key
     */
    public static final String JOB_DATA_MAP_KEY = "param";

    @PostConstruct
    private void startScheduler() {
        try {
            scheduler.clear();
            scheduler.start();
            QueryWrapper<ScheduledTask> queryWrapper = new QueryWrapper<>();
            List<ScheduledTask> list = scheduledTaskMapper.selectList(queryWrapper);
            for (ScheduledTask task : list) {
                this.createJob(task);
                if (task.getJobStatus() == 2) {
                    this.pauseJob(task);
                }
            }
        } catch (SchedulerException e) {
            log.error("定时任务启动异常  e --->>> {} ", e.toString());
        }
    }

    /**
     * 校验表达式
     *
     * @param cron 表达式
     * @return
     */
    public Boolean checkCron(String cron) {
        try {
            CronScheduleBuilder.cronScheduleNonvalidatedExpression(cron);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 校验是否存在任务
     *
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @return
     */
    public Boolean checkExists(String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            return scheduler.checkExists(jobKey);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 立即执行一次定时任务
     * @param task
     */
    public void startNowJob(StartNowJobDTO dto, ScheduledTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            // 任务名称和组构成任务实例
            JobDetail jobDetail = JobBuilder.newJob(EasyJobTaskExecute.class)
                    .withIdentity(task.getJobName(), task.getJobGroup())
                    .build();
            // 设置job参数
            jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, task);
            // 设置触发器
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    // 执行次数
                    .withRepeatCount(dto.getCount() - 1);
            // 执行间隔
            switch (dto.getUnit()) {
                case 1:
                    simpleScheduleBuilder.withIntervalInHours(dto.getInterval());
                    break;
                case 2:
                    simpleScheduleBuilder.withIntervalInMinutes(dto.getInterval());
                    break;
                case 3:
                    simpleScheduleBuilder.withIntervalInSeconds(dto.getInterval());
                    break;
                case 4:
                    simpleScheduleBuilder.withIntervalInMilliseconds(dto.getInterval());
                    break;
                default:
                    throw new JobException("执行间隔单位不正确");
            }
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobKey)
                    .withSchedule(simpleScheduleBuilder)
                    .build();
            if (scheduler.checkExists(jobKey)) {
                // 存在该任务，再绑定一个执行器
                scheduler.scheduleJob(trigger);
            } else {
                // 不存在该任务，绑定执行器
                scheduler.scheduleJob(jobDetail ,trigger);
            }
        } catch (SchedulerException e) {
            log.error("执行定时任务失败  e -->> {}" , e.toString());
            throw new JobException("执行定时任务失败");
        }
    }

    /**
     * 创建定时任务
     *
     * @param task 任务参数
     */
    public void createJob(ScheduledTask task) {
        if (!checkCron(task.getCron())) {
            throw new BusinessException("定时任务cron表达式异常");
        }
        try {
            // 任务名称和组构成任务实例
            JobDetail jobDetail = JobBuilder.newJob(EasyJobTaskExecute.class)
                    .withIdentity(task.getJobName(), task.getJobGroup())
                    .build();
            // 设置job参数
            jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, task);
            // 设置触发器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
            switch (task.getInstruction()) {
                case 0:
                    cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                    break;
                case 1:
                    cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                    break;
                case 2:
                    cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
                    break;
                default:
                    throw new BusinessException("超时策略不正确");
            }
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .forJob(jobKey)
                    .withSchedule(cronScheduleBuilder)
                    .build();
            // job已经存在
            if (scheduler.checkExists(jobKey)) {
                throw new JobException("定时任务已存在");
            }
            // 将任务与触发器关联
            scheduler.scheduleJob(jobDetail, trigger);
            if (task.getJobStatus() == 2) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("创建定时任务失败  e -->> {}" , e.toString());
            throw new JobException("创建定时任务失败");
        }
    }

    /**
     * 编辑定时任务
     * @param task
     */
    public void updateJob(ScheduledTask task) {
        if (!checkCron(task.getCron())) {
            throw new BusinessException("定时任务cron表达式异常");
        }
        try {
            // 任务名称和组构成任务实例
            JobDetail jobDetail = JobBuilder.newJob(EasyJobTaskExecute.class)
                    .withIdentity(task.getJobName(), task.getJobGroup())
                    .build();
            // 设置job参数
            jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, task);
            // 设置触发器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobKey)
                    .withSchedule(cronScheduleBuilder)
                    .build();
            // job存在，删除后新增
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
                scheduler.scheduleJob(jobDetail, trigger);
//                // 重新发布定时任务 - 这种方法无法实时生效
//                TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());
//                scheduler.rescheduleJob(triggerKey, trigger);
                if (task.getJobStatus() == 2) {
                    scheduler.pauseJob(jobKey);
                }
            }
        } catch (SchedulerException e) {
            log.error("编辑定时任务失败  e -->> {}" , e.toString());
            throw new JobException("编辑定时任务失败");
        }
    }

    /**
     * 删除定时任务
     *
     * @param task
     * @return
     */
    public boolean deleteJob(ScheduledTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                return scheduler.deleteJob(jobKey);
            }
            return true;
        } catch (SchedulerException e) {
            log.error("删除定时任务失败  e -->> {}" , e.toString());
            throw new JobException("删除定时任务失败");
        }
    }

    /**
     * 批量删除定时任务
     *
     * @param tasks
     * @return
     */
    public boolean deleteJobs(List<ScheduledTask> tasks) {
        try {
            List<JobKey> jobKeys = new ArrayList<>();
            for (ScheduledTask task : tasks) {
                JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
                if (scheduler.checkExists(jobKey)) {
                    jobKeys.add(jobKey);
                }
            }
            if (jobKeys.isEmpty()) {
                return true;
            }
            return scheduler.deleteJobs(jobKeys);
        } catch (SchedulerException e) {
            log.error("批量删除定时任务失败  e -->> {}" , e.toString());
            throw new JobException("批量删除定时任务失败");
        }
    }

    /**
     * 暂停定时任务
     *
     * @param task
     */
    public void pauseJob(ScheduledTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败  e -->> {}" , e.toString());
            throw new JobException("暂停定时任务失败");
        }
    }


    /**
     * 恢复定时任务
     *
     * @param task
     */
    public void resumeJob(ScheduledTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败  e -->> {}" , e.toString());
            throw new JobException("恢复定时任务失败");
        }
    }

}
