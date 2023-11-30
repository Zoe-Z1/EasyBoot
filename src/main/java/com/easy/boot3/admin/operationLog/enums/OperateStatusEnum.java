package com.easy.boot3.admin.operationLog.enums;

/**
 * @author zoe
 * @describe 操作状态
 * @date 2023/7/22
 */
public enum OperateStatusEnum {
    /**
     * 成功
     */
    SUCCESS("成功"),

    /**
     * 失败
     */
    FAIL("失败"),

    ;

    private String msg;

    public String getMsg(){
        return this.msg;
    }

    OperateStatusEnum(String msg){
        this.msg = msg;
    }
}
