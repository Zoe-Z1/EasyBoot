package com.fast.start.common.config;

import com.fast.start.common.properties.FastFile;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/8/7
 * @description 静态资源访问映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private FastFile fastFile;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fastFile.getFileMapPath() + "**").addResourceLocations("file:" + fastFile.getFilePath());
        registry.addResourceHandler(fastFile.getImageMapPath() + "**").addResourceLocations("file:" + fastFile.getImagePath());
    }

}
