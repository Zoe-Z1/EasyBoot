package com.easy.boot3.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot3.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot3.common.base.BaseEntity;
import com.easy.boot3.common.excel.EasyExcelSelect;
import com.easy.boot3.common.generator.DataMap;
import com.easy.boot3.common.generator.GenConstant;
import com.easy.boot3.common.generator.config.AnnotationConfig;
import com.easy.boot3.common.generator.config.FilterConfig;
import com.easy.boot3.common.generator.config.GlobalConfig;
import com.easy.boot3.common.generator.db.MetaTable;
import com.easy.boot3.utils.JsonUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 实体类模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityTemplate extends AbstractTemplate {

    private String remarks;

    private String moduleName;

    private Class<?> superClass;

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

    /**
     * 全局排除时保留的属性
     */
    private Set<String> includeField;

    @Override
    protected String getRemarks(String tableRemarks) {
        if (StrUtil.isNotEmpty(remarks)) {
            return remarks;
        }
        return tableRemarks;
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
        if (superClass == null) {
            return BaseEntity.class;
        } else {
            return superClass;
        }
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

    protected Set<String> getIncludeField() {
        if (includeField == null) {
            includeField = new HashSet<>();
        }
        return includeField;
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
        String javaName = metaTable.getBeanName();
        String className = getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
        if (getSuperClass() != null) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getSimpleName());
        }
    }

    /**
     * 处理实体类属性
     * @param buildDataMap 已构建过的参数
     */
    private void handleField(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        FilterConfig filter = buildDataMap.getFilterConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        // 不要直接获取处理，会导致其他地方没有数据
        List<GenerateColumn> columns = JsonUtil.copyList(metaTable.getColumns(), GenerateColumn.class);
        columns.removeIf(item -> filter.getExcludeField().contains(item.getJavaName()) && !getIncludeField().contains(item.getJavaName()));
        Class<?> clazz = getSuperClass();
        if (getEnableExcludeSuperField() && clazz != null) {
            java.lang.reflect.Field[] superFields = clazz.getDeclaredFields();
            Set<String> superFieldSet = Arrays.stream(superFields).map(java.lang.reflect.Field::getName).collect(Collectors.toSet());
            columns.removeIf(item -> superFieldSet.contains(item.getJavaName()));
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_TYPE, "entity");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_COLUMNS, columns);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENABLE_TABLE_FIELD, getEnableTableField());
        if (global.getEnableExport() || global.getEnableImport()) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_ENABLE_EXCEL, true);
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        AnnotationConfig annotation = buildDataMap.getAnnotationConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        Set<String> pkgs = new HashSet<>();
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        if (annotation.getEnableBuilder()) {
            pkgs.add("lombok.experimental.SuperBuilder");
        }
        if (global.getEnableExport() || global.getEnableImport()) {
            pkgs.add(ExcelProperty.class.getName());
            pkgs.add(EasyExcelSelect.class.getName());
        }
        pkgs.add(TableId.class.getName());
        pkgs.add(TableField.class.getName());
        pkgs.add(TableName.class.getName());
        pkgs.add(Schema.class.getName());
        List<GenerateColumn> columns = (List<GenerateColumn>) buildDataMap.get(GenConstant.DATA_MAP_KEY_COLUMNS);
        pkgs.addAll(columns.stream().map(GenerateColumn::getJavaTypePackageName).collect(Collectors.toSet()));
        pkgs.add("lombok.*");
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }

}
