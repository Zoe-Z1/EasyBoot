package com.easy.boot.api.login.controller;

import com.easy.boot.api.login.service.MobileLoginService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
@Api(tags = "登录接口")
@Validated
@RestController
@RequestMapping("/api")
public class MobileLoginController {

    @Resource
    private MobileLoginService mobileLoginService;

}
