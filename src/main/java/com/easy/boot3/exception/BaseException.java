package com.easy.boot3.exception;

/**
 * @describe 异常基类
 * @author zoe
 *
 * @date 2023/7/21
 */
public class BaseException extends RuntimeException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
