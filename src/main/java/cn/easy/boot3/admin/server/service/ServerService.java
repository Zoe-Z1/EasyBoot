package cn.easy.boot3.admin.server.service;

import cn.easy.boot3.admin.server.entity.ServerVO;

/**
 * @author zoe
 * @date 2023/8/13
 * @description 服务器监控
 */
public interface ServerService {

    /**
     * 获取服务器信息
     * @return
     */
    ServerVO getServerInfo();
}
