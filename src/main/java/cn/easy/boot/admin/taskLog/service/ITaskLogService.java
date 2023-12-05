package cn.easy.boot.admin.taskLog.service;

import cn.easy.boot.admin.taskLog.entity.TaskLog;
import cn.easy.boot.admin.taskLog.entity.TaskLogQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zoe
* @date 2023/08/06
* @description 调度日志 服务类
*/
public interface ITaskLogService extends IService<TaskLog> {

    /**
    * 查询调度日志列表
    * @param query
    * @return
    */
    IPage<TaskLog> selectPage(TaskLogQuery query);

    /**
    * 获取调度日志详情
    * @param id
    * @return
    */
    TaskLog detail(Long id);

    /**
    * 异步批量保存调度日志
    * @param taskLogs
    * @return
    */
    void asyncBatchSave(List<TaskLog> taskLogs);

    /**
    * 删除调度日志
    * @param id
    * @return
    */
    Boolean deleteById(Long id);

    /**
    * 批量删除调度日志
    * @param ids
    * @return
    */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 清空调度日志
     * @return
     */
    Boolean clear();
}
