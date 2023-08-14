package com.easy.boot.admin.loginLog.mapper;

import com.easy.boot.admin.loginLog.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 Mapper接口
*/
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
