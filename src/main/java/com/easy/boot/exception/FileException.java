package com.easy.boot.exception;

import com.easy.boot.exception.enums.SystemErrorEnum;

/**
 * @describe 文件相关异常
 * @author zoe
 * @date 2023/8/6
 */
public class FileException extends SystemException {

    public FileException() {
        super(SystemErrorEnum.UPLOAD_ERROR.getCode(), SystemErrorEnum.UPLOAD_ERROR.getMessage());
    }

    public FileException(SystemErrorEnum systemError) {
        super(systemError.getCode(), systemError.getMessage());
    }


    public FileException(String message) {
        super(SystemErrorEnum.UPLOAD_ERROR.getCode(), message);
    }

    public FileException(Integer code, String message) {
        super(code, message);
    }

}
