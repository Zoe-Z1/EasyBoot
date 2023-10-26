package com.easy.boot.common.saToken;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.common.redis.RedisKeyEnum;

/**
 * @author zoe
 * @date 2023/9/21
 * @description 用户上下文信息获取
 */
public class UserContext {

    /**
     * 未登录用户ID
     */
    public static final Long BY = -1L;

    /**
     * 未登录用户账号
     */
    public static final String USERNAME = "未知用户";


    /**
     * 登录
     * @return
     */
    public static void login(AdminUser user) {
        StpLogic stpLogic = new StpLogic(String.valueOf(RoleTypeEnum.WEB));
        StpUtil.setStpLogic(stpLogic);
        StpUtil.login(user.getId());
        setAdminUser(user.getId(), user);
    }

    /**
     * 注销登录
     * @return
     */
    public static void logout() {
        StpUtil.logoutByTokenValue(StpUtil.getTokenValue());
    }

    /**
     * 踢人下线并清除缓存
     * @return
     */
    public static void kickoutAndRemoveCache(Long userId) {
        removeAdminUser(userId);
        StpUtil.kickout(userId);
    }

    /**
     * 踢除会话
     * @return
     */
    public static void kickout() {
        StpUtil.kickoutByTokenValue(StpUtil.getTokenValue());
    }

    /**
     * 踢除指定会话
     * @return
     */
    public static void kickout(String token) {
        StpUtil.kickoutByTokenValue(token);
    }

    /**
     * 判断用户是否登录
     * @return
     */
    public static Boolean isLogin() {
        boolean isLogin = false;
        try{
            isLogin = StpUtil.isLogin();
        }catch (Exception ignored) {
        }
        return isLogin;
    }

    /**
     * 判断管理端用户是否登录
     * @return
     */
    public static void checkAdminUserLogin() {
        StpLogic stpLogic = new StpLogic(String.valueOf(RoleTypeEnum.WEB));
        StpUtil.setStpLogic(stpLogic);
        StpUtil.checkLogin();
    }

    /**
     * 获取用户ID
     * @return
     */
    public static Long getId() {
        Long id = BY;
        if (isLogin()) {
            id = StpUtil.getLoginIdAsLong();
        }
        return id;
    }

    /**
     * 获取用户账号
     * @return
     */
    public static String getUsername() {
        String username = USERNAME;
        if (isLogin()) {
            username = getAdminUser().getUsername();
        }
        return username;
    }

    /**
     * 获取token name
     * @return
     */
    public static String getTokenName() {
        return SaManager.getConfig().getTokenName();
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        return SaManager.getConfig().getTokenPrefix() + " " + StpUtil.getTokenValue();
    }

    /**
     * 存储登录用户信息
     * @param id
     * @param adminUser
     */
    public static void setAdminUser(Long id, AdminUser adminUser) {
        setAdminUser(id, adminUser, -1);
    }

    /**
     * 存储登录用户信息
     * @param id
     * @param adminUser
     * @param timeout
     */
    public static void setAdminUser(Long id, AdminUser adminUser, long timeout) {
        SaManager.getSaTokenDao().setObject(RedisKeyEnum.SA_USER_DETAIL.getKey(id), adminUser, timeout);
    }

    /**
     * 清除登录用户信息
     * @param id
     * @return
     */
    public static void removeAdminUser(Long id) {
        SaManager.getSaTokenDao().deleteObject(RedisKeyEnum.SA_USER_DETAIL.getKey(id));
    }

    /**
     * 清除登录用户信息
     * @return
     */
    public static void removeAdminUser() {
        Long id = getId();
        if (id.equals(BY)) {
            return;
        }
        SaManager.getSaTokenDao().deleteObject(RedisKeyEnum.SA_USER_DETAIL.getKey(id));
    }

    /**
     * 获取登录用户信息
     * @return
     */
    public static AdminUser getAdminUser() {
        Long id = getId();
        return getAdminUser(id);
    }

    /**
     * 获取登录用户信息
     * @param id
     * @return
     */
    public static AdminUser getAdminUser(Long id) {
        AdminUser user = (AdminUser) SaManager.getSaTokenDao().getObject(RedisKeyEnum.SA_USER_DETAIL.getKey(id));
        return user;
    }

    /**
     * 获取用户角色类型
     * @return
     */
    public static RoleTypeEnum getRoleType() {
        RoleTypeEnum roleType = RoleTypeEnum.UNKNOWN;
        if (isLogin()) {
            roleType = RoleTypeEnum.valueOf(StpUtil.getLoginType());
        }
        return roleType;
    }
}
