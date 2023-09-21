package com.easy.boot.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.easy.boot.common.saToken.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author zoe
 * @describe mybatis-plus 自动填充策略实现
 * @date 2023/7/22
 */
@Component
public class EasyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Long createBy = (Long) metaObject.getValue("createBy");
        if (createBy == null) {
            createBy = UserContext.getId();
            this.setFieldValByName("createBy", createBy, metaObject);
        }
        String createUsername = (String) metaObject.getValue("createUsername");
        if (createUsername == null) {
            createUsername = UserContext.getUsername();
            this.setFieldValByName("createUsername", createUsername, metaObject);
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
            updateBy = UserContext.getId();
            this.setFieldValByName("updateBy", updateBy, metaObject);
        }
        String updateUsername = (String) metaObject.getValue("updateUsername");
        if (updateUsername == null) {
            updateUsername = UserContext.getUsername();
            this.setFieldValByName("updateUsername", updateUsername, metaObject);
        }
        Long updateTime = (Long) metaObject.getValue("updateTime");
        if (updateTime == null) {
            this.setFieldValByName("updateTime", DateUtil.current(), metaObject);
        }
    }
}
