package cn.easy.boot3.admin.sysConfigDomain.enums;

/**
 * @author zoe
 * @date 2023/10/26
 * @description 系统配置域编码枚举
 */
public enum SysConfigDomainCodeEnum {

    GLOBAL("global", "全局参数配置"),

    SYSTEM("system", "系统参数配置"),


    ;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    SysConfigDomainCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
