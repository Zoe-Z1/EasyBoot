package com.easy.boot.common.saToken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.easy.boot.admin.login.service.AdminLoginService;
import com.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/7/21
 * @description 统一权限拦截
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Resource
    private AdminLoginService adminLoginService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter.match("/admin/**", r -> {
                adminLoginService.checkLogin();
            });
            SaRouter.match("/api/**", r -> {
                StpLogic stpLogic = new StpLogic(String.valueOf(RoleTypeEnum.MOBILE));
                StpUtil.setStpLogic(stpLogic);
                StpUtil.checkLogin();
            });
        }))
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/login",
                        "/admin/test",
                        "/admin/user/download",
                        "/api/login",
                        "/favicon.ico",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/webjars/**");
    }
}
