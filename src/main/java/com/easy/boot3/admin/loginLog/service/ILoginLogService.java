package com.easy.boot3.admin.loginLog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot3.admin.loginLog.entity.LoginLog;
import com.easy.boot3.admin.loginLog.entity.LoginLogCreateDTO;
import com.easy.boot3.admin.loginLog.entity.LoginLogQuery;

import java.util.List;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 服务类
*/
public interface ILoginLogService extends IService<LoginLog> {

    /**
    * 查询登录日志
    * @param query
    * @return
    */
    IPage<LoginLog> selectPage(LoginLogQuery query);

    /**
    * 异步创建登录日志
    * @param dto
    * @return
    */
    void asyncCreate(LoginLogCreateDTO dto);

    /**
     * 获取登录日志
     * @param userAgent 代理信息
     * @param ip ip地址
     * @return
     */
    LoginLog getLoginLog(String userAgent, String ip);

    /**
     * 删除登录日志
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除登录日志
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 清空登录日志
     * @return
     */
    Boolean clear();

    /**
     * 获取登录人数
     * @param startTime
     * @param endTime
     * @return
     */
    Long getLoginNumber(Long startTime, Long endTime);

    /**
     * 获取访问IP数
     * @param startTime
     * @param endTime
     * @return
     */
    Long getIpNumber(Long startTime, Long endTime);

}
