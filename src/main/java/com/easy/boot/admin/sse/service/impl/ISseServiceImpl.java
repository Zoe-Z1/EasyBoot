package com.easy.boot.admin.sse.service.impl;

import com.easy.boot.admin.sse.service.ISseService;
import com.easy.boot.common.sse.SseMessage;
import com.easy.boot.common.sse.SseServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;

/**
 * @author zoe
 * @date 2023/10/23
 * @description SSE 实现
 */
@Slf4j
@Service
public class ISseServiceImpl implements ISseService {

    @Override
    public SseEmitter connect(String sessionKey) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onError((err)-> {
            log.error("type: SseServer Error, msg: {} session Id : {}",err.getMessage(), sessionKey);
            SseServer.onError(sessionKey, err);
        });

        sseEmitter.onTimeout(() -> {
            log.info("type: SseServer Timeout, session Id : {}", sessionKey);
            SseServer.remove(sessionKey);
        });

        sseEmitter.onCompletion(() -> {
            log.info("type: SseServer Completion, session Id : {}", sessionKey);
            SseServer.remove(sessionKey);
        });
        SseServer.add(sessionKey, sseEmitter);
        SseMessage message = new SseMessage(0);
        SseServer.send(sessionKey, message);
        return sseEmitter;
    }

    @Override
    public void send(String sessionKey, SseMessage message) {
        SseServer.send(sessionKey, message);
    }

    @Override
    public void batchSend(Set<String> sessionKeys, SseMessage message) {
        SseServer.batchSend(sessionKeys, message);
    }

    @Override
    public void sendAll(SseMessage message) {
        SseServer.sendAll(message);;
    }

    @Override
    public void close(String sessionKey) {
        log.info("type: SseServer Close, session Id : {}", sessionKey);
        SseServer.remove(sessionKey);
    }
}
