package com.easy.boot.common.redis;

import com.easy.boot.admin.onlineUser.service.IOnlineUserService;
import com.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/10/25
 * @description redis key到期监听实现
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private IOnlineUserService onlineUserService;

    @Value("${sa-token.token-name}")
    private String tokenName;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String prefix = String.join(":", tokenName, String.valueOf(RoleTypeEnum.WEB), "token:");
        // 监听指定key
        if (message.toString().startsWith(prefix)) {
            String token = message.toString().replace(prefix, "");
            onlineUserService.deleteByToken(token);
        }
    }
}
