package com.easy.boot.common.base;


import com.easy.boot.common.properties.EasyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zoe
 * @date 2023/7/22
 * @description 控制器基类
 */
public class BaseController {

    @Resource
    protected EasyFile easyFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    protected Long maxLimit;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;
}
