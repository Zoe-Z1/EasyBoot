package cn.easy.boot.common.config;

import cn.easy.boot.common.properties.EasyFile;
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
    private EasyFile easyFile;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(easyFile.getFileMapPath() + "**").addResourceLocations("file:" + easyFile.getFilePath());
        registry.addResourceHandler(easyFile.getImageMapPath() + "**").addResourceLocations("file:" + easyFile.getImagePath());
    }

}
