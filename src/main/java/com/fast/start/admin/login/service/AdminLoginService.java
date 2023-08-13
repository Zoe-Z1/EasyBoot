package com.fast.start.admin.login.service;


import com.fast.start.admin.login.entity.LoginDTO;
import com.fast.start.admin.user.entity.AdminUser;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
public interface AdminLoginService {

    /**
     * 用户登录
     * @param dto
     * @return
     */
    AdminUser login(LoginDTO dto);

    /**
     * 检查登录状态
     * @return
     */
    void checkLogin();
}
