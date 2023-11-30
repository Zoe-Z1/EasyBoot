package com.easy.boot3.exception;

import com.easy.boot3.exception.enums.SystemErrorEnum;

/**
 * @describe 获取锁异常
 * @author zoe
 * @date 2023/8/4
 */
public class LockException extends BaseException {

    public LockException() {
        super(SystemErrorEnum.TRY_LOCK_ERROR.getCode(), SystemErrorEnum.TRY_LOCK_ERROR.getMessage());
    }

    public LockException(SystemErrorEnum systemError) {
        super(systemError.getCode(), systemError.getMessage());
    }


    public LockException(String message) {
        super(SystemErrorEnum.TRY_LOCK_ERROR.getCode(), message);
    }

    public LockException(Integer code, String message) {
        super(code, message);
    }

}
