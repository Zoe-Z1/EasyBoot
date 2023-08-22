package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zoe
 * @date 2023/8/15
 * @description DTO模板配置
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateDTOTemplate extends AbstractTemplate {

    private String modulePath;

    public Class<?> superClass;

    public String templateName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModulePath() {
        if (StrUtil.isEmpty(modulePath)) {
            modulePath = "/entity";
        }
        return modulePath;
    }

    @Override
    public Class<?> getSuperClass() {
        return null;
    }

    @Override
    public String getTemplateName() {
        return "createDTO.ftl";
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
