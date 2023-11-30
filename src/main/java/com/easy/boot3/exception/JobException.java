package com.easy.boot3.exception;

import com.easy.boot3.exception.enums.SystemErrorEnum;

/**
 * @author zoe
 * @date 2023/8/5
 * @description 定时任务异常
 */
public class JobException extends SystemException {

    public JobException() {
        super(SystemErrorEnum.SCHEDULER_ERROR.getCode(), SystemErrorEnum.SCHEDULER_ERROR.getMessage());
    }

    public JobException(String message) {
        super(SystemErrorEnum.SYSTEM_ERROR.getCode(), message);
    }
}
