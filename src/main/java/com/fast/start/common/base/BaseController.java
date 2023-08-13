package com.fast.start.common.base;


import com.fast.start.common.noRepeatSubmit.FastNoRepeatSubmit;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zoe
 * @date 2023/7/22
 * @description 控制器基类
 */
@FastNoRepeatSubmit
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;
}
