package cn.easy.boot.common.saToken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.easy.boot.admin.login.service.AdminLoginService;
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
            SaRouter.match("/common/**", r -> {
                adminLoginService.checkLogin();
            });
        }))
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/login", "/admin/logout", "/admin/code", "/admin/validate/code/*",
                        "/admin/sysConfigDomain/global/all",
                        "/admin/sysConfigDomain/system/all",
                        "/favicon.ico",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/webjars/**");
    }
}
