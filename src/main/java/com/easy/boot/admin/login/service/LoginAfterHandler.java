package com.easy.boot.admin.login.service;

import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.user.entity.AdminUser;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录后处理器
 */
public interface LoginAfterHandler {

    /**
     * 登录后处理
     * @param adminUser
     * @param dto
     * @return
     */
    LoginHandlerAfterDO handler(AdminUser adminUser, LoginDTO dto);
}
