package com.easy.boot.admin.loginLog.mapper;

import com.easy.boot.admin.loginLog.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 Mapper接口
*/
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 获取访问IP数
     * @param startTime
     * @param endTime
     * @return
     */
    Long getIpNumber(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
