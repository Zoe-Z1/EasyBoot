package com.easy.boot.admin.login.service;


import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.user.entity.AdminUser;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
public interface AdminLoginService {

    /**
     * 用户登录
     * @param dto
     * @return
     */
    AdminUser login(LoginDTO dto);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 检查登录状态
     * @return
     */
    void checkLogin();

    /**
     * 获取验证码
     */
    CaptchaResponse<ImageCaptchaVO> getCode();

    /**
     * 校验验证码
     * @param id
     * @param track
     * @return
     */
    ApiResponse<?> validateCode(String id, ImageCaptchaTrack track);
}
