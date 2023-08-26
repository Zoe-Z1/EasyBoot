package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.MetaTable;
import lombok.*;

import java.util.*;

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

    private String moduleName;

    public Class<?> superClass;

    public String templateName;

    public String fileName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = GenConstant.MODULE_ENTITY;
        }
        return moduleName;
    }

    @Override
    public Class<?> getSuperClass() {
        if (superClass == null) {
            return BaseEntity.class;
        } else {
            return superClass;
        }
    }

    @Override
    public String getTemplateName() {
        return GenConstant.ENTITY_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.SUFFIX;
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

    @Override
    public DataMap buildDataMap(DataMap dataMap) {
        DataMap buildDataMap = super.buildDataMap(dataMap);
        // 构建类名
        buildClassName(buildDataMap);
        // 构建需要导入的包
        buildPkgDataMap(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildClassName(DataMap buildDataMap) {
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        String javaName = metaTable.getBeanName();
        String className = template.getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
        if (getSuperClass() != null) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        AnnotationConfig annotation = buildDataMap.getAnnotationConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        String pkg = buildDataMap.getString(GenConstant.DATA_MAP_KEY_PKG);
        Set<String> pkgs = new HashSet<>();
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        if (annotation.getEnableBuilder()) {
            pkgs.add(Builder.class.getName());
        }
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
        pkg = String.join(".",pkg, getModuleName());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKG, pkg);
    }

}
