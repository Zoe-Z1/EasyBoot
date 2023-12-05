package cn.easy.boot3.admin.operationLog.mapper;

import cn.easy.boot3.admin.home.entity.HandlerTimeDO;
import cn.easy.boot3.admin.home.entity.HotsApiDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.easy.boot3.admin.operationLog.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 操作日志 Mapper接口
*/
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 获取热点接口列表
     * @param todayBeginTime
     * @param limit
     * @return
     */
    List<HotsApiDO> getHotsApi(@Param("todayBeginTime") long todayBeginTime, @Param("limit")  int limit);

    /**
     * 获取接口处理时长列表
     * @param todayBeginTime
     * @param limit
     * @return
     */
    List<HandlerTimeDO> getHandlerTime(@Param("todayBeginTime") long todayBeginTime, @Param("limit")  int limit);
}
