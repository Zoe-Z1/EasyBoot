package com.easy.boot.admin.login.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.login.service.LoginAfterHandler;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.common.redis.EasyRedisManager;
import com.easy.boot.common.redis.RedisKeyEnum;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录后状态检查处理器
 */
@Order(1)
@Service
public class LoginAfterHandlerCheckStatusImpl implements LoginAfterHandler {

    @Resource
    private EasyRedisManager easyRedisManager;

    @Override
    public LoginHandlerAfterDO handler(AdminUser user, LoginDTO dto) {
        LoginHandlerAfterDO afterDO = LoginHandlerAfterDO.builder()
                .status(true)
                .build();
        String key = RedisKeyEnum.ADMIN_LOGIN_CAPTCHA.getKey(dto.getId());
        String id = easyRedisManager.getString(key);
        easyRedisManager.remove(key);
        if (StrUtil.isEmpty(id)) {
            afterDO.setStatus(false)
                    .setMessage("非法的登录请求");
            return afterDO;
        }
        if (user == null) {
            afterDO.setStatus(false)
                    .setMessage("账号或密码错误");
        } else if (user.getStatus() == 2) {
            afterDO.setStatus(false)
                    .setMessage("账号已被禁用，无法登录");
        }
        return afterDO;
    }
}
