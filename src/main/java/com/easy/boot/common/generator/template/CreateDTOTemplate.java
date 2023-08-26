package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.MetaTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

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
        return superClass;
    }

    @Override
    public String getTemplateName() {
        return GenConstant.CREATE_DTO_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.CREATE_DTO + GenConstant.SUFFIX;
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
        // 构建父类名
        buildSuperClassName(buildDataMap);
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
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String javaName = metaTable.getBeanName();
        String className = template.getCreateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put("className", className);
    }

    /**
     * 构建父类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildSuperClassName(DataMap buildDataMap) {
        TemplateConfig template = buildDataMap.getTemplateConfig();
        if (template.getCreateDTO().getSuperClass() != null) {
            buildDataMap.put("superName", template.getCreateDTO().getSuperClass().getName());
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
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        Set<String> pkgs = new HashSet<>();
        if (template.getCreateDTO().getSuperClass() != null) {
            pkgs.add(template.getCreateDTO().getSuperClass().getName());
        }
        if (annotation.getEnableBuilder()) {
            pkgs.add(Builder.class.getName());
        }
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }
}
