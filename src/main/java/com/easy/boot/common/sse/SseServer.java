package com.easy.boot.common.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zoe
 * @date 2023/10/23
 * @description SSE推送处理
 */
@Slf4j
public class SseServer {

    /**
     * 当前连接数
     */
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 连接用户集
     */
    private static Map<String, SseEmitter> sessionMap = new ConcurrentHashMap<>();

    /**
     * 增加新连接用户
     * @param sessionKey
     * @param sseEmitter
     */
    public static void add(String sessionKey, SseEmitter sseEmitter) {
        sessionMap.putIfAbsent(sessionKey, sseEmitter);
    }

    /**
     * 判断用户是否已连接
     * @param sessionKey
     * @return true：已连接 false：未连接
     */
    public static boolean exists(String sessionKey) {
        return sessionMap.get(sessionKey) != null;
    }

    /**
     * 断开用户连接
     * @param sessionKey
     * @return
     */
    public static void remove(String sessionKey) {
        SseEmitter sseEmitter = sessionMap.get(sessionKey);
        if (sseEmitter != null) {
            sessionMap.remove(sessionKey);
            sseEmitter.complete();
        }
    }

    /**
     * 发生连接异常
     * @param sessionKey
     * @param throwable
     */
    public static void onError(String sessionKey, Throwable throwable) {
        SseEmitter sseEmitter = sessionMap.get(sessionKey);
        if (sseEmitter != null) {
            sseEmitter.completeWithError(throwable);
        }
    }

    /**
     * 发送消息
     * @param sessionKey
     * @param message
     */
    public static void send(String sessionKey, SseMessage message) {
        try {
            if (exists(sessionKey)) {
                sessionMap.get(sessionKey).send(message);
            }
        } catch (IOException e) {
            log.error("sessionKey -> {} 发送数据 {} 异常", sessionKey, message);
        } catch (IllegalStateException e) {
            sessionMap.remove(sessionKey);
            log.error("sessionKey -> {} 发送数据 {} 异常", sessionKey, message);
        }
    }

    /**
     * 批量发送消息
     * @param sessionKeys
     * @param message
     */
    public static void batchSend(Set<String> sessionKeys, SseMessage message) {
        sessionKeys.forEach(sessionKey -> send(sessionKey, message));
    }

    /**
     * 给所有人发送消息
     * @param message
     */
    public static void sendAll(SseMessage message) {
        sessionMap.forEach((key, value) -> {
            try {
                value.send(message);
            } catch (IOException e) {
                log.error("sessionKey -> {} 发送数据 {} 异常", key, message);
            } catch (IllegalStateException e) {
                sessionMap.remove(key);
                log.error("sessionKey -> {} 发送数据 {} 异常", key, message);
            }
        });
    }

}
