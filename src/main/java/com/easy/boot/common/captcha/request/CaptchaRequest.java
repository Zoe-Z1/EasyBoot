package com.easy.boot.common.captcha.request;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: 天爱有情
 * @Date 2020/6/19 16:26
 * @Description 验证码请求对象
 */
@Data
public class CaptchaRequest<T> {

    @NotEmpty(message = "验证码ID不能为空")
    private String id;

    @NotNull(message = "滑动轨迹不能为空")
    private ImageCaptchaTrack captchaTrack;

    @Valid
    private T form;
}