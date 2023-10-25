package com.easy.boot.admin.login.service.impl;

import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.login.service.LoginAfterHandler;
import com.easy.boot.admin.user.entity.AdminUser;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录后状态检查处理器
 */
@Order(1)
@Service
public class LoginAfterHandlerCheckStatusImpl implements LoginAfterHandler {

    @Override
    public LoginHandlerAfterDO handler(AdminUser user, LoginDTO dto) {
        LoginHandlerAfterDO afterDO = LoginHandlerAfterDO.builder()
                .status(true)
                .build();
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
