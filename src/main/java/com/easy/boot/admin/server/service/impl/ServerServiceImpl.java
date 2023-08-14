package com.easy.boot.admin.server.service.impl;

import com.easy.boot.admin.server.entity.ServerVO;
import com.easy.boot.admin.server.service.ServerService;
import com.easy.boot.utils.ServerUtil;
import org.springframework.stereotype.Service;

/**
 * @author zoe
 * @date 2023/8/13
 * @description
 */
@Service
public class ServerServiceImpl implements ServerService {


    @Override
    public ServerVO getServerInfo() {
        return ServerVO.builder()
                .userDir(System.getProperties().getProperty("user.dir"))
                .cpu(ServerUtil.getMyCpu())
                .os(ServerUtil.getMyOs())
                .memory(ServerUtil.getMyMemory())
                .disk(ServerUtil.getMyDisk())
                .jvm(ServerUtil.getMyJvm())
                .network(ServerUtil.getMyNetwork())
                .build();
    }
}
