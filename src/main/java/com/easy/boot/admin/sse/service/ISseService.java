package com.easy.boot.admin.sse.service;

import com.easy.boot.common.sse.SseMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;

/**
* @author zoe
* @date 2023/10/23
* @description SSE 服务类
*/
public interface ISseService {

    /**
     * 创建sse连接
     * @param sessionKey
     * @return
     */
    SseEmitter connect(String sessionKey);

    /**
     * 推送消息
     * @param sessionKey
     * @param message
     * @return
     */
    void send(String sessionKey, SseMessage message);

    /**
     * 批量推送消息
     * @param sessionKeys
     * @param message
     * @return
     */
    void batchSend(Set<String> sessionKeys, SseMessage message);

    /**
     * 给所有人推送消息
     * @param message
     * @return
     */
    void sendAll(SseMessage message);

    /**
     * 关闭连接
     * @param sessionKey
     * @return
     */
    void close(String sessionKey);
}
