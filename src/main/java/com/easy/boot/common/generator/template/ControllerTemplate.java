package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.base.BaseController;
import lombok.*;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 控制层模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControllerTemplate extends AbstractTemplate {

    private String modulePath;

    private String className;

    public Class<?> superClass;

    public String templateName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModulePath() {
        if (StrUtil.isEmpty(modulePath)) {
            modulePath = "/controller";
        }
        return modulePath;
    }

    @Override
    protected String getClassName() {
        return className;
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
