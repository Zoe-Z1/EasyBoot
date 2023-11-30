package com.easy.boot3.admin.operationLog.enums;

/**
 * @author zoe
 * @describe 角色类型枚举
 * @date 2023/7/22
 */
public enum RoleTypeEnum {

    /**
     * 未知
     */
    UNKNOWN("未知"),

    /**
     * 移动端用户
     */
    MOBILE("移动端用户"),

    /**
     * WEB端用户
     */
    WEB("WEB端用户"),

    ;

    private String msg;

    public String getMsg(){
        return this.msg;
    }

    RoleTypeEnum(String msg){
        this.msg = msg;
    }
}
