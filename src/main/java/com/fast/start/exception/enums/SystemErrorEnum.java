package com.fast.start.exception.enums;

/**
 * @author zoe
 * @describe 系统返回枚举
 * @date 2023/7/21
 */
public enum SystemErrorEnum {

    /*------------------------系统级别初始返回枚举------------------------*/

    /** 操作成功 */
    SUCCESS(200,"操作成功"),

    /** 操作失败 */
    FAIL(201,"操作失败"),

    /** 登录失效 */
    INVALID(203,"登录已失效"),

    /** 禁止重复提交 */
    FORBID_REPEAT_SUBMIT(204,"请求过于频繁"),

    /** IP未知 */
    IP_UNKNOWN(205,"IP未知，无法访问"),

    /** 没有角色权限 */
    NO_ROLE(401,"没有角色权限"),

    /** 没有菜单权限 */
    NO_PERMISSION(402,"没有菜单权限"),

    /** 认证失败 */
    AUTHENTICATION_FAILURE(403,"身份认证失败,请重新授权"),

    /** 找不到请求路径异常 */
    NOT_FOUND(404, "找不到请求的路径"),

    /** 系统出错 */
    SYSTEM_ERROR(500, "系统出错"),

    /** 调用方法出错 */
    CALL_ERROR(501,"调用方法出错"),

    /** 字段效验出错 */
    FIELD_ERROR(502,"字段效验出错"),

    /** 定时任务执行错误 */
    SCHEDULER_ERROR(503,"定时任务执行出错"),

    /** 数据解析出错 */
    JSON_PARSE_ERROR(504,"数据解析异常"),

    /** 文件上传出错 */
    UPLOAD_ERROR(505,"文件上传异常"),

    /** 文件大小超出 */
    UPLOAD_MAX_SIZE_EXCEEDED_ERROR(506,"文件大小超过限制"),

    /** 下载文件出错 */
    DOWNLOAD_ERROR(507,"下载文件异常"),

    /** 业务逻辑出错 */
    BUSINESS_ERROR(600,"业务逻辑出错"),

    /** http请求异常 */
    HTTP_REQUEST_ERROR(700,"http请求异常"),

    /** 获取不到锁 */
    TRY_LOCK_ERROR(800,"获取锁异常"),


    /*------------------------用户异常返回枚举------------------------*/

    USER_ERROR(1000,"用户异常"),

    USERNAME_OR_PASSWORD_ERROR(1001,"用户名或密码错误"),

    USER_DISABLED(1002,"账号已被禁用，无法操作"),

    USER_IS_BLACKLIST(1003,"账号已被拉黑，无法操作"),

    IP_IS_BLACKLIST(1003,"IP已被拉黑，无法操作"),

    USER_NULL_ERROR(1004,"用户不存在"),



    /*------------------------验证码异常返回枚举------------------------*/

    RENDER_ERROR(1100,"获取验证码异常"),

    VALIDATE_ERROR(1101,"验证码错误"),

    CAPTCHA_EXPIRED(1102,"验证码已过期"),


    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    SystemErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
