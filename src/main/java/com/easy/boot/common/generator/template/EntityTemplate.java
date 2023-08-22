package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.base.BaseEntity;
import lombok.*;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 实体类模板配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityTemplate extends AbstractTemplate {

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
        return BaseEntity.class;
    }

    @Override
    public String getTemplateName() {
        return "entity.ftl";
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
