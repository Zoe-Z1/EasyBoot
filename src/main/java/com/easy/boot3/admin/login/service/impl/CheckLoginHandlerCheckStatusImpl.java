package com.easy.boot3.admin.login.service.impl;

import com.easy.boot3.admin.login.service.CheckLoginHandler;
import com.easy.boot3.admin.user.entity.AdminUser;
import com.easy.boot3.admin.user.service.AdminUserService;
import com.easy.boot3.exception.BusinessException;
import com.easy.boot3.exception.enums.SystemErrorEnum;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录状态检查器
 */
@Order(1)
@Service
public class CheckLoginHandlerCheckStatusImpl implements CheckLoginHandler {

    @Resource
    private AdminUserService adminUserService;

    @Override
    public void check(Long id) {
        AdminUser user = adminUserService.detail(id);
        if (user == null) {
            throw new BusinessException(SystemErrorEnum.USER_ERROR);
        } else if (user.getStatus() == 2) {
            throw new BusinessException(SystemErrorEnum.USER_DISABLED);
        }
    }
}
