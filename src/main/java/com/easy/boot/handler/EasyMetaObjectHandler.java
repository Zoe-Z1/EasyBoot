package com.easy.boot.handler;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.easy.boot.admin.user.entity.AdminUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author zoe
 * @describe mybatis-plus 自动填充策略实现
 * @date 2023/7/22
 */
@Component
public class EasyMetaObjectHandler implements MetaObjectHandler {

    /**
     * -1 代表未登录用户
     */
    public static final Long BY = -1L;

    public static final String USERNAME = "未知用户";

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean isLogin = false;
        try{
            isLogin = StpUtil.isLogin();
        }catch (Exception ignored) {
        }
        Long createBy = (Long) metaObject.getValue("createBy");
        if (createBy == null) {
            createBy = EasyMetaObjectHandler.BY;
            if (isLogin) {
                createBy = StpUtil.getLoginIdAsLong();
            }
            this.setFieldValByName("createBy", createBy, metaObject);
        }
        String createUsername = (String) metaObject.getValue("createUsername");
        if (createUsername == null) {
            createUsername = EasyMetaObjectHandler.USERNAME;
            if (isLogin) {
                AdminUser user = (AdminUser) SaManager.getSaTokenDao().getObject(String.valueOf(createBy));
                createUsername = user.getUsername();
            }
            this.setFieldValByName("createUsername", createUsername, metaObject);
        }
        Long createTime = (Long) metaObject.getValue("createTime");
        if (createTime == null) {
            this.setFieldValByName("createTime", DateUtil.current(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        boolean isLogin = false;
        try{
            isLogin = StpUtil.isLogin();
        }catch (Exception e) {
        }
        Long updateBy = (Long) metaObject.getValue("updateBy");
        if (updateBy == null) {
            updateBy = EasyMetaObjectHandler.BY;
            if (isLogin) {
                updateBy = StpUtil.getLoginIdAsLong();
            }
            this.setFieldValByName("updateBy", updateBy, metaObject);
        }
        String updateUsername = (String) metaObject.getValue("updateUsername");
        if (updateUsername == null) {
            updateUsername = EasyMetaObjectHandler.USERNAME;
            if (isLogin) {
                AdminUser user = (AdminUser) SaManager.getSaTokenDao().getObject(String.valueOf(updateBy));
                updateUsername = user.getUsername();
            }
            this.setFieldValByName("updateUsername", updateUsername, metaObject);
        }
        Long updateTime = (Long) metaObject.getValue("updateTime");
        if (updateTime == null) {
            this.setFieldValByName("updateTime", DateUtil.current(), metaObject);
        }
    }
}
