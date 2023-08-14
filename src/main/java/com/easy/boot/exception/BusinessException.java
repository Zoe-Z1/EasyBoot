package com.easy.boot.exception;


import com.easy.boot.exception.enums.SystemErrorEnum;

/**
 * @describe 业务异常，在正常业务中出现的所有异常
 * @author zoe
 *
 * @date 2023/7/21
 */
public class BusinessException extends BaseException {

    public BusinessException(){
        super(SystemErrorEnum.BUSINESS_ERROR.getCode(), SystemErrorEnum.BUSINESS_ERROR.getMessage());
    }

    public BusinessException(SystemErrorEnum systemErrorEnum){
        super(systemErrorEnum.getCode(),systemErrorEnum.getMessage());
    }

    public BusinessException(String message){
        super(SystemErrorEnum.BUSINESS_ERROR.getCode(),message);
    }

    public BusinessException(Integer code, String message){
        super(code,message);
    }

}
