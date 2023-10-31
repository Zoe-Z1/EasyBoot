//package com.easy.boot.common.captcha;
//
//import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
//import cloud.tianai.captcha.common.constant.CommonConstant;
//import cloud.tianai.captcha.resource.common.model.dto.Resource;
//import cloud.tianai.captcha.resource.impl.DefaultResourceStore;
//import cloud.tianai.captcha.resource.impl.provider.URLResourceProvider;
//import org.springframework.stereotype.Component;
//
///**
// * @author zoe
// * @date 2023/10/29
// * @description 自定义验证码背景图
// */
//@Component
//public class EasyResourceStore extends DefaultResourceStore {
//
//    public EasyResourceStore() {
//        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(URLResourceProvider.NAME, "https://pic2.zhimg.com/80/v2-1384bedc285171d35d44ddcf55952ea5_1440w.webp", CommonConstant.DEFAULT_TAG));
//        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(URLResourceProvider.NAME, "https://pic1.zhimg.com/80/v2-9e127a3fbe3e8223668fe45b18422790_1440w.webp", CommonConstant.DEFAULT_TAG));
//        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(URLResourceProvider.NAME, "https://pic3.zhimg.com/80/v2-e82927942623a828b8c5021e087ef732_1440w.webp", CommonConstant.DEFAULT_TAG));
//        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(URLResourceProvider.NAME, "https://pic1.zhimg.com/80/v2-8bae2d3c3552eb3f2311b435b1129f6c_1440w.webp", CommonConstant.DEFAULT_TAG));
//        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(URLResourceProvider.NAME, "https://pic3.zhimg.com/80/v2-3497e1897e65fce08e61f4a0d0a8a4d6_1440w.webp", CommonConstant.DEFAULT_TAG));
//        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(URLResourceProvider.NAME, "https://pic2.zhimg.com/80/v2-67e405327d3f0d4b11c5fbe34af15129_1440w.webp", CommonConstant.DEFAULT_TAG));
//    }
//}
