package com.easy.boot.handler;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
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

    @Override
    public void insertFill(MetaObject metaObject) {
        Long createBy = (Long) metaObject.getValue("createBy");
        if (createBy == null) {
            createBy = EasyMetaObjectHandler.BY;
            boolean isLogin = false;
            try{
                isLogin = StpUtil.isLogin();
            }catch (Exception e) {
            }
            if (isLogin) {
                createBy = Long.valueOf(String.valueOf(StpUtil.getLoginId()));
            }
            this.setFieldValByName("createBy", createBy, metaObject);
        }
        Long createTime = (Long) metaObject.getValue("createTime");
        if (createTime == null) {
            this.setFieldValByName("createTime", DateUtil.current(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long updateBy = (Long) metaObject.getValue("updateBy");
        if (updateBy == null) {
            updateBy = EasyMetaObjectHandler.BY;
            boolean isLogin = false;
            try{
                isLogin = StpUtil.isLogin();
            }catch (Exception e) {
            }
            if (isLogin) {
                updateBy = Long.valueOf(String.valueOf(StpUtil.getLoginId()));
            }
            this.setFieldValByName("updateBy", updateBy, metaObject);
        }
        Long updateTime = (Long) metaObject.getValue("updateTime");
        if (updateTime == null) {
            this.setFieldValByName("updateTime", DateUtil.current(), metaObject);
        }
    }
}
