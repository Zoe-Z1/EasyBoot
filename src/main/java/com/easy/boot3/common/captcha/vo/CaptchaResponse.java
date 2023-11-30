package com.easy.boot3.common.captcha.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 天爱有情
 * @Date 2020/5/29 8:31
 * @Description 验证码返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse<T> implements Serializable {

    @Schema(title = "验证ID")
    private String id;

    @Schema(title = "验证码信息")
    private T captcha;

    public static <T> CaptchaResponse<T> of(String id, T data) {
        return new CaptchaResponse<T>(id, data);
    }
}
