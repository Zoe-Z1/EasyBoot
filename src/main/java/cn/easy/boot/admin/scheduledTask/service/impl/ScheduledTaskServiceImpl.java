package cn.easy.boot.admin.scheduledTask.service.impl;

import cn.easy.boot.admin.scheduledTask.entity.*;
import cn.easy.boot.admin.scheduledTask.mapper.ScheduledTaskMapper;
import cn.easy.boot.admin.scheduledTask.service.IScheduledTaskService;
import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.common.quartz.EasyJobManager;
import cn.easy.boot.utils.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot.admin.scheduledTask.entity.*;
import cn.easy.boot.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* @author zoe
* @date 2023/08/04
* @description 定时任务 服务实现类
*/
@Service
public class ScheduledTaskServiceImpl extends ServiceImpl<ScheduledTaskMapper, ScheduledTask> implements IScheduledTaskService {

    @Resource
    private EasyJobManager easyJobManager;

    @Override
    public void startNow(StartNowJobDTO dto) {
        ScheduledTask task = this.getById(dto.getId());
        easyJobManager.startNowJob(dto, task);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void change(ChangeJobDTO dto) {
        ScheduledTask task = this.getById(dto.getId());
        task.setJobStatus(dto.getJobStatus());
        boolean status = this.updateById(task);
        if (status) {
            if (task.getJobStatus() == 1) {
                easyJobManager.resumeJob(task);
            } else {
                easyJobManager.pauseJob(task);
            }
        }
    }

    @Override
    public IPage<ScheduledTask> selectPage(ScheduledTaskQuery query) {
        Page<ScheduledTask> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(ScheduledTask::getJobName, query.getKeyword()).or()
                            .like(ScheduledTask::getJobGroup, query.getKeyword()).or()
                            .like(ScheduledTask::getJobKey, query.getKeyword());
                })
                .eq(Objects.nonNull(query.getJobStatus()), ScheduledTask::getJobStatus, query.getJobStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(ScheduledTask::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public ScheduledTask detail(Long id) {
        return getById(id);
    }

    @Override
    public ScheduledTask getByNameAndGroup(String jobName, String jobGroup) {
        return lambdaQuery()
                .eq(ScheduledTask::getJobName, jobName)
                .eq(ScheduledTask::getJobGroup, jobGroup)
                .one();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean create(ScheduledTaskCreateDTO dto) {
        ScheduledTask task = this.getByNameAndGroup(dto.getJobName(), dto.getJobGroup());
        if (Objects.nonNull(task)) {
            throw new BusinessException("存在相同名称和分组的定时任务");
        }
        ScheduledTask entity = BeanUtil.copyBean(dto, ScheduledTask.class);
        boolean status = save(entity);
        if (status) {
            easyJobManager.createJob(entity);
        }
        return status;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateById(ScheduledTaskUpdateDTO dto) {
        ScheduledTask task = this.getByNameAndGroup(dto.getJobName(), dto.getJobGroup());
        if (Objects.nonNull(task) && !task.getId().equals(dto.getId())) {
            throw new BusinessException("存在相同名称和分组的定时任务");
        }
        task = this.getById(dto.getId());
        ScheduledTask entity = BeanUtil.copyBean(dto, ScheduledTask.class);
        boolean status = updateById(entity);
        if (status) {
            // 删除旧的任务，创建新任务 这样才可以立即生效
            easyJobManager.deleteJob(task);
            easyJobManager.createJob(entity);
        }
        return status;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Long id) {
        ScheduledTask task = this.getById(id);
        boolean status = this.removeById(id);
        if (status) {
            status = easyJobManager.deleteJob(task);
        }
        return status;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        List<ScheduledTask> tasks = this.listByIds(ids);
        boolean status = this.removeBatchByIds(ids);
        if (status) {
            status = easyJobManager.deleteJobs(tasks);
        }
        return status;
    }

}
