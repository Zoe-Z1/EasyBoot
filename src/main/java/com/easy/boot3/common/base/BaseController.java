package com.easy.boot3.common.base;


import com.easy.boot3.common.properties.EasyFile;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
