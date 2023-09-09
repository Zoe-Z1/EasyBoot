package com.easy.boot.common.generator.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.db.Field;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.utils.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
     * 是否开启继承createDTO模式，
     * 开启此模式后，superClass设置将无效，updateDTO将继承createDTO
     * 实体类仅生成指定属性，使用 includeField 指定需要生成的属性
     */
    private Boolean enableExtendsCreateDTO;

    /**
     * 全局排除时保留的属性
     */
    private Set<String> includeField;

    private String templateName;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

    /**
     * 是否生成@TableField
     */
    private Boolean enableTableField;

    /**
     * 忽略父类已存在的属性
     * 开启此配置后，父类存在的属性，子类不再生成
     */
    private Boolean enableExcludeSuperField;

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

    @Override
    protected Class<?> getSuperClass() {
        return superClass;
    }

    protected Boolean getEnableExtendsCreateDTO() {
        if (enableExtendsCreateDTO == null) {
            enableExtendsCreateDTO = true;
        }
        return enableExtendsCreateDTO;
    }

    protected Set<String> getIncludeField() {
        if (includeField == null) {
            includeField = new HashSet<>();
        }
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

    public Boolean getEnableTableField() {
        if (enableTableField == null) {
            enableTableField = false;
        }
        return enableTableField;
    }

    public Boolean getEnableExcludeSuperField() {
        if (enableExcludeSuperField == null) {
            enableExcludeSuperField = true;
        }
        return enableExcludeSuperField;
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
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getSimpleName());
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
        FilterConfig filter = buildDataMap.getFilterConfig();
        List<Field> fields = JsonUtil.copyList(metaTable.getFields(), Field.class);
        if (getEnableExtendsCreateDTO()) {
            Set<String> includeFields = getIncludeField();
            fields.removeIf(item -> !includeFields.contains(item.getJavaName()));
        } else {
            fields.removeIf(item -> filter.getExcludeField().contains(item.getJavaName()) && !getIncludeField().contains(item.getJavaName()));
            Class<?> clazz = getSuperClass();
            if (getEnableExcludeSuperField() && clazz != null) {
                java.lang.reflect.Field[] superFields = clazz.getDeclaredFields();
                Set<String> superFieldSet = Arrays.stream(superFields).map(java.lang.reflect.Field::getName).collect(Collectors.toSet());
                fields.removeIf(item -> superFieldSet.contains(item.getJavaName()));
            }
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_FIELDS, fields);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENABLE_TABLE_FIELD, getEnableTableField());
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
            pkgs.add(SuperBuilder.class.getName());
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
        private Set<String> includeField;
        private String templateName;
        private String fileName;
        private Boolean enable;
        private Boolean isOverride;
        private Boolean enableTableField;
        private Boolean enableExcludeSuperField;

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
            this.superClass = superClass;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder enableExtendsCreateDTO(final Boolean enableExtendsCreateDTO) {
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

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder enableTableField(final Boolean enableTableField) {
            this.enableTableField = enableTableField;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder enableExcludeSuperField(final Boolean enableExcludeSuperField) {
            this.enableExcludeSuperField = enableExcludeSuperField;
            return this;
        }

        public UpdateDTOTemplate.UpdateDTOTemplateBuilder addIncludeField(String... includeField) {
            this.includeField = CollUtil.newHashSet(includeField);
            return this;
        }

        public UpdateDTOTemplate build() {
            return new UpdateDTOTemplate(this.remarks, this.moduleName, this.superClass, this.enableExtendsCreateDTO, this.includeField, this.templateName, this.fileName, this.enable, this.isOverride, this.enableTableField, this.enableExcludeSuperField);
        }
    }
}
