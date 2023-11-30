package com.easy.boot3.common.generator.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot3.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot3.common.excel.entity.ImportExcelError;
import com.easy.boot3.common.generator.DataMap;
import com.easy.boot3.common.generator.GenConstant;
import com.easy.boot3.common.generator.config.FilterConfig;
import com.easy.boot3.common.generator.config.GlobalConfig;
import com.easy.boot3.common.generator.config.TemplateConfig;
import com.easy.boot3.common.generator.db.MetaTable;
import com.easy.boot3.utils.BeanUtil;
import com.easy.boot3.utils.JsonUtil;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
public class ServiceImplTemplate extends AbstractTemplate {

    private String remarks;

    private String moduleName;

    private Class<?> superClass;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

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
            moduleName = GenConstant.MODULE_SERVICE_IMPL;
        }
        return moduleName;
    }

    @Override
    protected Class<?> getSuperClass() {
        if (superClass == null) {
            return ServiceImpl.class;
        } else {
            return superClass;
        }
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.SERVICE_IMPL_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.SERVICE_IMPL + GenConstant.SUFFIX;
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
        GlobalConfig global = buildDataMap.getGlobalConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        String javaName = metaTable.getBeanName();
        String camelName = metaTable.getCamelName();
        String className = getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String serviceName = template.getService().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String mapperName = template.getMapper().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityName = template.getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityCamelName = template.getEntity().getFileName(camelName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_SERVICE_NAME, serviceName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_MAPPER_NAME, mapperName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_NAME, entityName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_CAMEL_NAME, entityCamelName);
        if (template.getCreateDTO().isEnable()) {
            String createDTOName = template.getCreateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_CREATE_DTO_NAME, createDTOName);
        }
        if (template.getUpdateDTO().isEnable()) {
            String updateDTOName = template.getUpdateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_UPDATE_DTO_NAME, updateDTOName);
        }
        if (template.getQuery().isEnable()) {
            String queryName = template.getQuery().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_QUERY_NAME, queryName);
        }
        if (template.getVo().isEnable()) {
            String voName = template.getVo().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_VO_NAME, voName);
        }
        if (getSuperClass() != null) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getSimpleName());
        }
        if (global.getEnableImport()) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_IMPORT_EXCEL_ERROR_NAME, ImportExcelError.class.getSimpleName());
        }
    }

    /**
     * 处理实体类属性
     * @param buildDataMap 已构建过的参数
     */
    private void handleField(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        FilterConfig filter = buildDataMap.getFilterConfig();
        // 不要直接获取处理，会导致其他地方没有数据
        List<GenerateColumn> columns = JsonUtil.copyList(metaTable.getColumns(), GenerateColumn.class);
        columns.removeIf(item -> filter.getExcludeField().contains(item.getJavaName()));
        List<String> keywordFields = columns.stream()
                .filter(item -> item.getIsKeyword() == 0)
                .map(GenerateColumn::getJavaName).collect(Collectors.toList());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_KEYWORD_FIELDS, keywordFields);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_COLUMNS, columns);
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        Set<String> pkgs = new HashSet<>();
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        if (global.getEnableImport()) {
            pkgs.add(ImportExcelError.class.getName());
            pkgs.add(CollUtil.class.getName());
            pkgs.add(Iterator.class.getName());
        }
        pkgs.add(Service.class.getName());
        pkgs.add(Page.class.getName());
        pkgs.add(BeanUtil.class.getName());
        pkgs.add(StrUtil.class.getName());
        pkgs.add(IPage.class.getName());
        pkgs.add(IPage.class.getName());
        pkgs.add(List.class.getName());
        String pkgName = String.join(".", global.getPackageName(), metaTable.getModuleName());
        String serviceName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_SERVICE_NAME);
        String mapperName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_MAPPER_NAME);
        String entityName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_ENTITY_NAME);
        ServiceTemplate serviceTemplate = buildDataMap.getTemplateConfig().getService();
        MapperTemplate mapperTemplate = buildDataMap.getTemplateConfig().getMapper();
        EntityTemplate entityTemplate = buildDataMap.getTemplateConfig().getEntity();
        CreateDTOTemplate createDTOTemplate = buildDataMap.getTemplateConfig().getCreateDTO();
        UpdateDTOTemplate updateDTOTemplate = buildDataMap.getTemplateConfig().getUpdateDTO();
        QueryTemplate queryTemplate = buildDataMap.getTemplateConfig().getQuery();
        VOTemplate voTemplate = buildDataMap.getTemplateConfig().getVo();
        String servicePkgName = String.join(".", pkgName, serviceTemplate.getModuleName(), serviceName);
        String mapperPkgName = String.join(".", pkgName, mapperTemplate.getModuleName(), mapperName);
        String entityPkgName = String.join(".", pkgName, entityTemplate.getModuleName(), entityName);
        pkgs.add(servicePkgName);
        pkgs.add(mapperPkgName);
        pkgs.add(entityPkgName);
        if (createDTOTemplate.isEnable()) {
            String createDTOName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_CREATE_DTO_NAME);
            String createDTOPkgName = String.join(".", pkgName, createDTOTemplate.getModuleName(), createDTOName);
            pkgs.add(createDTOPkgName);
        }
        if (updateDTOTemplate.isEnable()) {
            String updateDTOName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_UPDATE_DTO_NAME);
            String updateDTOPkgName = String.join(".", pkgName, updateDTOTemplate.getModuleName(), updateDTOName);
            pkgs.add(updateDTOPkgName);
        }
        if (queryTemplate.isEnable()) {
            String queryName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_QUERY_NAME);
            String queryPkgName = String.join(".", pkgName, queryTemplate.getModuleName(), queryName);
            pkgs.add(queryPkgName);
        }
        if (voTemplate.isEnable()) {
            String voName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_VO_NAME);
            String voPkgName = String.join(".", pkgName, voTemplate.getModuleName(), voName);
            pkgs.add(voPkgName);
        }

        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }
}
