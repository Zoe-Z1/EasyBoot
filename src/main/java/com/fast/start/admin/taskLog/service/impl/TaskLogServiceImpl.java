package com.fast.start.admin.taskLog.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.start.admin.taskLog.entity.TaskLog;
import com.fast.start.admin.taskLog.entity.TaskLogQuery;
import com.fast.start.admin.taskLog.mapper.TaskLogMapper;
import com.fast.start.admin.taskLog.service.ITaskLogService;
import com.fast.start.common.base.BaseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author zoe
* @date 2023/08/06
* @description 调度日志 服务实现类
*/
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements ITaskLogService {

    @Override
    public IPage<TaskLog> selectPage(TaskLogQuery query) {
        Page<TaskLog> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .like(StrUtil.isNotEmpty(query.getJobName()), TaskLog::getJobName, query.getJobName())
                .like(StrUtil.isNotEmpty(query.getJobGroup()), TaskLog::getJobGroup, query.getJobGroup())
                .like(StrUtil.isNotEmpty(query.getJobKey()), TaskLog::getJobKey, query.getJobKey())
                .eq(StrUtil.isNotEmpty(query.getStatus()), TaskLog::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public TaskLog detail(Long id) {
        return getById(id);
    }

    @Async("LogThreadPoolTaskExecutor")
    @Override
    public void asyncBatchSave(List<TaskLog> taskLogs) {
        if (CollUtil.isEmpty(taskLogs)) {
            return;
        }
        saveBatch(taskLogs);
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

    @Override
    public Boolean clear() {
        QueryWrapper<TaskLog> queryWrapper = new QueryWrapper<>();
        return remove(queryWrapper);
    }

}
