package com.easy.boot.admin.sysConfig.enums;

/**
 * @author zoe
 * @date 2023/10/26
 * @description 系统配置域编码枚举
 */
public enum DomainCodeEnum {

    GLOBAL("global", "全局参数配置"),




    ;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    DomainCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
