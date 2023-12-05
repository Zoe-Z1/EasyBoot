package cn.easy.boot3.admin.operationLog.service;

import cn.easy.boot3.admin.home.entity.HandlerTimeDO;
import cn.easy.boot3.admin.home.entity.HotsApiDO;
import cn.easy.boot3.admin.operationLog.entity.OperationLogQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.easy.boot3.admin.operationLog.entity.OperationLog;

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

    /**
     * 获取操作数
     * @param startTime
     * @param endTime
     * @return
     */
    Long getOperationNumber(Long startTime, Long endTime);

    /**
     * 获取热点接口列表
     * @param todayBeginTime
     * @param limit
     * @return
     */
    List<HotsApiDO> getHotsApi(long todayBeginTime, int limit);

    /**
     * 获取接口处理时长列表
     * @param todayBeginTime
     * @param limit
     * @return
     */
    List<HandlerTimeDO> getHandlerTime(long todayBeginTime, int limit);
}
