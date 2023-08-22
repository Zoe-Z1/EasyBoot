package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.base.BaseController;
import lombok.*;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 服务层模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceTemplate extends AbstractTemplate {

    private String modulePath;

    public Class<?> superClass;

    public String templateName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModulePath() {
        if (StrUtil.isEmpty(modulePath)) {
            modulePath = "/service";
        }
        return modulePath;
    }

    @Override
    public Class<?> getSuperClass() {
        return BaseController.class;
    }

    @Override
    public String getTemplateName() {
        return "controller.ftl";
    }

    @Override
    public Boolean isEnable() {
        if (enable == null) {
            enable = true;
        }
        return enable;
    }

    @Override
    protected Boolean getIsOverride() {
        return isOverride;
    }
}
