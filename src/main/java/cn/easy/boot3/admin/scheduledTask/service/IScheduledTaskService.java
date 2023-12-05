package cn.easy.boot3.admin.scheduledTask.service;

import cn.easy.boot3.admin.scheduledTask.entity.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.scheduledTask.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zoe
* @date 2023/08/04
* @description 定时任务 服务类
*/
public interface IScheduledTaskService extends IService<ScheduledTask> {

    /**
     * 立即执行定时任务
     * @param dto
     * @return
     */
    void startNow(StartNowJobDTO dto);

    /**
     * 恢复/暂停定时任务
     * @param dto
     */
    void change(ChangeJobDTO dto);

    /**
    * 查询定时任务
    * @param query
    * @return
    */
    IPage<ScheduledTask> selectPage(ScheduledTaskQuery query);

    /**
     * 获取定时任务详情
     * @param id
     * @return
     */
    ScheduledTask detail(Long id);

    /**
     * 根据任务名和任务分组获取任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    ScheduledTask getByNameAndGroup(String jobName, String jobGroup);

    /**
    * 创建定时任务
    * @param dto
    * @return
    */
    Boolean create(ScheduledTaskCreateDTO dto);

    /**
    * 编辑定时任务
    * @param dto
    * @return
    */
    Boolean updateById(ScheduledTaskUpdateDTO dto);

    /**
     * 删除定时任务
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除定时任务
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

}
