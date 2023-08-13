package com.fast.start.admin.operationLog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.start.admin.operationLog.entity.OperationLog;
import com.fast.start.admin.operationLog.entity.OperationLogQuery;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 操作日志 服务类
*/
public interface IOperationLogService extends IService<OperationLog> {

    /**
    * 查询操作日志
    * @param query
    * @return
    */
    IPage<OperationLog> selectPage(OperationLogQuery query);

    /**
     * 获取操作日志详情
     * @param id
     * @return
     */
    OperationLog detail(Long id);

    /**
     * 异步保存日志
     * @param entity
     */
    void asyncSaveLog(OperationLog entity);

    /**
     * 删除操作日志
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除操作日志
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 清空操作日志
     * @return
     */
    Boolean clear();
}
