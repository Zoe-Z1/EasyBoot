package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.GenConstant;
import lombok.*;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 测试模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TestTemplate extends AbstractTemplate {

    private String moduleName;

    public Class<?> superClass;

    public String templateName;

    public String fileName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = "test";
        }
        return moduleName;
    }

    @Override
    public Class<?> getSuperClass() {
        return superClass;
    }

    @Override
    public String getTemplateName() {
        return "test.ftl";
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.TEST + GenConstant.SUFFIX;
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
