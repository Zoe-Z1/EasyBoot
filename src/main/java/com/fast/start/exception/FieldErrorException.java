package com.fast.start.exception;


import com.fast.start.exception.enums.SystemErrorEnum;
import com.fast.start.handler.GlobalExceptionHandler;

import java.util.List;

/**
 * @describe 字段效验错误异常
 * @author zoe
 *
 * @date 2023/7/23
 */
public class FieldErrorException extends BaseException {

    private List<GlobalExceptionHandler.FieldError> errors;

    public List<GlobalExceptionHandler.FieldError> getErrors() {
        return errors;
    }

    public FieldErrorException(List<GlobalExceptionHandler.FieldError> errors) {
        super(SystemErrorEnum.FIELD_ERROR.getCode(), SystemErrorEnum.FIELD_ERROR.getMessage());
        this.errors = errors;
    }
}
