package com.easy.boot.common.base;


import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zoe
 * @date 2023/7/22
 * @description 控制器基类
 */
@EasyNoRepeatSubmit
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;
}
