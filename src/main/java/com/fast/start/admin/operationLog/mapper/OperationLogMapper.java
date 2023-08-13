package com.fast.start.admin.operationLog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.start.admin.operationLog.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zoe
* @date 2023/07/30
* @description 操作日志 Mapper接口
*/
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
