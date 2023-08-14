package com.easy.boot.admin.operationLog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.operationLog.entity.OperationLog;
import com.easy.boot.admin.operationLog.entity.OperationLogQuery;
import com.easy.boot.admin.operationLog.mapper.OperationLogMapper;
import com.easy.boot.admin.operationLog.service.IOperationLogService;
import com.easy.boot.common.base.BaseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author zoe
* @date 2023/07/30
* @description 操作日志 服务实现类
*/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {


    @Override
    public IPage<OperationLog> selectPage(OperationLogQuery query) {
        Page<OperationLog> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .like(StrUtil.isNotEmpty(query.getIp()), OperationLog::getIp, query.getIp())
                .like(StrUtil.isNotEmpty(query.getRequestWay()), OperationLog::getRequestWay, query.getRequestWay())
                .like(StrUtil.isNotEmpty(query.getRequestUrl()), OperationLog::getRequestUrl, query.getRequestUrl())
                .like(StrUtil.isNotEmpty(query.getOperateModule()), OperationLog::getOperateModule, query.getOperateModule())
                .eq(StrUtil.isNotEmpty(query.getOperateType()), OperationLog::getOperateType, query.getOperateType())
                .eq(StrUtil.isNotEmpty(query.getOperatorType()), OperationLog::getOperatorType, query.getOperatorType())
                .eq(StrUtil.isNotEmpty(query.getOperateStatus()), OperationLog::getOperateStatus, query.getOperateStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .ge(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 1,
                        OperationLog::getHandleTime, query.getHandleTime())
                .le(Objects.nonNull(query.getHandleTime()) && Objects.nonNull(query.getHandleTimeOperator()) && query.getHandleTimeOperator() == 2,
                        OperationLog::getHandleTime, query.getHandleTime())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public OperationLog detail(Long id) {
        return getById(id);
    }

    @Async("LogThreadPoolTaskExecutor")
    @Override
    public void asyncSaveLog(OperationLog log) {
        save(log);
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
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        return remove(queryWrapper);
    }

}
