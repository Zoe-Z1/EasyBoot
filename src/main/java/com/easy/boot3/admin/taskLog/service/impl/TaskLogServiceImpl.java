package com.easy.boot3.admin.taskLog.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot3.admin.taskLog.entity.TaskLog;
import com.easy.boot3.admin.taskLog.entity.TaskLogQuery;
import com.easy.boot3.admin.taskLog.mapper.TaskLogMapper;
import com.easy.boot3.admin.taskLog.service.ITaskLogService;
import com.easy.boot3.common.base.BaseEntity;
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
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(TaskLog::getJobName, query.getKeyword()).or()
                            .like(TaskLog::getJobGroup, query.getKeyword()).or()
                            .like(TaskLog::getJobKey, query.getKeyword()).or()
                            .like(TaskLog::getCron, query.getKeyword());
                })
                .eq(query.getTaskId() != null, TaskLog::getTaskId, query.getTaskId())
                .eq(query.getInstruction() != null, TaskLog::getInstruction, query.getInstruction())
                .like(StrUtil.isNotEmpty(query.getJobParams()), TaskLog::getJobParams, query.getJobParams())
                .eq(StrUtil.isNotEmpty(query.getStatus()), TaskLog::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        TaskLog::getStartTime, query.getStartTime(), query.getEndTime())
                .ge(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 1,
                        TaskLog::getHandleTime, query.getHandleTime())
                .le(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 2,
                        TaskLog::getHandleTime, query.getHandleTime())
                .gt(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 3,
                        TaskLog::getHandleTime, query.getHandleTime())
                .lt(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 4,
                        TaskLog::getHandleTime, query.getHandleTime())
                .eq(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 5,
                        TaskLog::getHandleTime, query.getHandleTime())
                .ne(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 6,
                        TaskLog::getHandleTime, query.getHandleTime())
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
