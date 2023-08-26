package com.easy.boot.common.generator.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.db.Field;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.exception.GeneratorException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/8/26
 * @description DTO模板配置
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateDTOTemplate extends AbstractTemplate {

    private String remarks;

    private String moduleName;

    private Class<?> superClass;

    /**
     * 是否继承createDTO模式，
     * 开启此模式后，updateDTO将继承createDTO，实体类仅生成指定属性
     * 使用 includeField 指定需要生成的属性
     */
    private Boolean enableExtendsCreateDTO;

    /**
     * 开启了enableExtendsCreateDTO后，需要保留的属性
     */
    private Set<String> includeField;

    private String templateName;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

    @Override
    protected String getRemarks(String tableRemarks) {
        if (StrUtil.isNotEmpty(remarks)) {
            return remarks;
        }
        return tableRemarks + "编辑";
    }

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = GenConstant.MODULE_ENTITY;
        }
        return moduleName;
    }

    public void setSuperClass(Class<?> superClass) {
        if (enableExtendsCreateDTO != null && superClass != null) {
            throw new GeneratorException("superClass属性与enableExtendsCreateDTO属性不可同时设置");
        }
        this.superClass = superClass;
    }

    @Override
    protected Class<?> getSuperClass() {
        return superClass;
    }

    public void setEnableExtendsCreateDTO(Boolean enableExtendsCreateDTO) {
        if (superClass != null && enableExtendsCreateDTO != null) {
            throw new GeneratorException("superClass属性与enableExtendsCreateDTO属性不可同时设置");
        }
        this.enableExtendsCreateDTO = enableExtendsCreateDTO;
    }

    protected Boolean getEnableExtendsCreateDTO() {
        if (enableExtendsCreateDTO == null) {
            enableExtendsCreateDTO = false;
        }
        return enableExtendsCreateDTO;
    }

    public Set<String> getIncludeField() {
        return includeField;
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.ENTITY_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.UPDATE_DTO + GenConstant.SUFFIX;
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
        // 处理实体类属性
        handleField(buildDataMap);
        // 构建需要导入的包
        buildPkgDataMap(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildClassName(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        CreateDTOTemplate template = buildDataMap.getTemplateConfig().getCreateDTO();
        String javaName = metaTable.getBeanName();
        String className = getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String createDTOName = template.getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
        if (getSuperClass() != null) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getName());
        }
        if (getEnableExtendsCreateDTO()) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, createDTOName);
        }
    }

    /**
     * 处理实体类属性
     * @param buildDataMap 已构建过的参数
     */
    private void handleField(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        List<Field> fields = new ArrayList<>(metaTable.getFields());
        if (getEnableExtendsCreateDTO()) {
            Set<String> includeFields = getIncludeField();
            fields.removeIf(item -> !includeFields.contains(item.getJavaName()));
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_FIELDS, fields);
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        AnnotationConfig annotation = buildDataMap.getAnnotationConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        Set<String> pkgs = new HashSet<>();
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        if (annotation.getEnableBuilder()) {
            if (getSuperClass() != null) {
                pkgs.add(SuperBuilder.class.getName());
            } else {
                if (getEnableExtendsCreateDTO()) {
                    pkgs.add(SuperBuilder.class.getName());
                } else {
                    pkgs.add(Builder.class.getName());
                }
            }
        }
        if (getEnableExtendsCreateDTO()) {
            GlobalConfig global = buildDataMap.getGlobalConfig();
            String createDTOPkgName = String.join(".", global.getPackageName(), metaTable.getModuleName());
            String createDTOName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_SUPER_NAME);
            CreateDTOTemplate template = buildDataMap.getTemplateConfig().getCreateDTO();
            createDTOPkgName = String.join(".", createDTOPkgName, template.getModuleName(), createDTOName);
            pkgs.add(createDTOPkgName);
        }
        pkgs.add(TableName.class.getName());
        pkgs.add(ApiModel.class.getName());
        pkgs.add(ApiModelProperty.class.getName());
        pkgs.addAll(metaTable.getFields().stream().map(Field::getJavaTypePackageName).collect(Collectors.toSet()));
        pkgs.add("lombok.*");
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }

    public static UpdateDTOTemplate.UpdateDTOTemplateBuilder builder() {
        return new UpdateDTOTemplate.UpdateDTOTemplateBuilder();
    }

    @ToString
    public static class UpdateDTOTemplateBuilder {
        private String remarks;
        private String moduleName;
        private Class<?> superClass;
        private Boolean enableExtendsCreateDTO;
        private String templateName;
        private String fileName;
        private Boolean enable;
        private Boolean isOverride;
        private Set<String> includeField;

        UpdateDTOTemplateBuilder() {
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder remarks(final String remarks) {
            this.remarks = remarks;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder moduleName(final String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder superClass(final Class<?> superClass) {
            if (enableExtendsCreateDTO != null && superClass != null) {
                throw new GeneratorException("superClass属性与enableExtendsCreateDTO属性不可同时设置");
            }
            this.superClass = superClass;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder enableExtendsCreateDTO(final Boolean enableExtendsCreateDTO) {
            if (enableExtendsCreateDTO != null && superClass != null) {
                throw new GeneratorException("superClass属性与enableExtendsCreateDTO属性不可同时设置");
            }
            this.enableExtendsCreateDTO = enableExtendsCreateDTO;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder templateName(final String templateName) {
            this.templateName = templateName;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder enable(final Boolean enable) {
            this.enable = enable;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder isOverride(final Boolean isOverride) {
            this.isOverride = isOverride;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder includeField(String... field) {
            this.includeField = CollUtil.newHashSet(field);
            return this;
        }

        public UpdateDTOTemplate build() {
            return new UpdateDTOTemplate(this.remarks, this.moduleName, this.superClass, this.enableExtendsCreateDTO, this.includeField, this.templateName, this.fileName, this.enable, this.isOverride);
        }
    }
}
