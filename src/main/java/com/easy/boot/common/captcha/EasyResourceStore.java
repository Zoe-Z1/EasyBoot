package com.easy.boot.common.captcha;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.constant.CommonConstant;
import cloud.tianai.captcha.generator.common.constant.SliderCaptchaConstant;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import cloud.tianai.captcha.resource.impl.DefaultResourceStore;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zoe
 * @date 2023/10/29
 * @description 自定义验证码资源
 */
@Component
public class EasyResourceStore extends DefaultResourceStore {

    public EasyResourceStore() {
        List<String> resources = CollUtil.newArrayList(
                "captcha/resource/1.jpg","captcha/resource/2.jpg","captcha/resource/3.jpg","captcha/resource/4.jpg","captcha/resource/5.jpg",
                "captcha/resource/6.jpg","captcha/resource/7.jpg","captcha/resource/8.jpg","captcha/resource/9.jpg","captcha/resource/10.jpg",
                "captcha/resource/11.jpg","captcha/resource/12.jpg","captcha/resource/13.jpg","captcha/resource/14.jpg","captcha/resource/15.jpg",
                "captcha/resource/16.jpg","captcha/resource/17.jpg","captcha/resource/18.jpg","captcha/resource/19.jpg","captcha/resource/20.jpg"
        );
        resources.forEach(item -> {
            addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(ClassPathResourceProvider.NAME, item, CommonConstant.DEFAULT_TAG));
            addResource(CaptchaTypeConstant.SLIDER, new Resource(ClassPathResourceProvider.NAME, item, CommonConstant.DEFAULT_TAG));
            addResource(CaptchaTypeConstant.CONCAT, new Resource(ClassPathResourceProvider.NAME, item, CommonConstant.DEFAULT_TAG));
            addResource(CaptchaTypeConstant.ROTATE, new Resource(ClassPathResourceProvider.NAME, item, CommonConstant.DEFAULT_TAG));
        });
        List<String> sliderActives = CollUtil.newArrayList(
                "captcha/template/1/active.png","captcha/template/2/active.png","captcha/template/3/active.png","captcha/template/4/active.png",
                "captcha/template/5/active.png"
        );
        List<String> sliderFixeds = CollUtil.newArrayList(
                "captcha/template/1/fixed.png","captcha/template/2/fixed.png","captcha/template/3/fixed.png","captcha/template/4/fixed.png",
                "captcha/template/5/fixed.png"
        );
        for (int i = 0; i < sliderActives.size(); i++) {
            ResourceMap template = new ResourceMap("default", 4);
            template.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, sliderActives.get(i)));
            template.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, sliderFixeds.get(i)));
            addTemplate(CaptchaTypeConstant.SLIDER, template);
        }
        List<String> rotateActives = CollUtil.newArrayList(
                "captcha/template/6/active.png","captcha/template/10/active.png"
        );
        List<String> rotateFixeds = CollUtil.newArrayList(
                "captcha/template/6/fixed.png","captcha/template/10/fixed.png"
        );
        for (int i = 0; i < rotateActives.size(); i++) {
            ResourceMap template = new ResourceMap("default", 4);
            template.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, rotateActives.get(i)));
            template.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, rotateFixeds.get(i)));
            addTemplate(CaptchaTypeConstant.ROTATE, template);
        }
    }
}
