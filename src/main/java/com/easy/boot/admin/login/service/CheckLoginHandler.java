package com.easy.boot.admin.login.service;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录状态检查器
 */
public interface CheckLoginHandler {

    /**
     * 登录状态检查
     * @param id
     * @return
     */
    void check(Long id);
}
