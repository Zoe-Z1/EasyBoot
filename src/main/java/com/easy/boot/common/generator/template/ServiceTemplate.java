package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.MetaTable;
import lombok.*;

import java.util.*;

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
public class ServiceTemplate extends AbstractTemplate {

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
            moduleName = GenConstant.MODULE_SERVICE;
        }
        return moduleName;
    }

    @Override
    protected Class<?> getSuperClass() {
        if (superClass == null) {
            return IService.class;
        } else {
            return superClass;
        }
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.SERVICE_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return "I" + javaName + GenConstant.SERVICE + GenConstant.SUFFIX;
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
        GlobalConfig global = buildDataMap.getGlobalConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        String javaName = metaTable.getBeanName();
        String camelName = metaTable.getCamelName();
        String className = getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityName = template.getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityCamelName = template.getEntity().getFileName(camelName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
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
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        Set<String> pkgs = new HashSet<>();
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        if (global.getEnableImport()) {
            pkgs.add(ImportExcelError.class.getName());
        }
        pkgs.add(IPage.class.getName());
        pkgs.add(List.class.getName());
        EntityTemplate entityTemplate = buildDataMap.getTemplateConfig().getEntity();
        CreateDTOTemplate createDTOTemplate = buildDataMap.getTemplateConfig().getCreateDTO();
        UpdateDTOTemplate updateDTOTemplate = buildDataMap.getTemplateConfig().getUpdateDTO();
        QueryTemplate queryTemplate = buildDataMap.getTemplateConfig().getQuery();
        VOTemplate voTemplate = buildDataMap.getTemplateConfig().getVo();
        String pkgName = String.join(".", global.getPackageName(), metaTable.getModuleName());
        String entityName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_ENTITY_NAME);
        String entityPkgName = String.join(".", pkgName, entityTemplate.getModuleName(), entityName);
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
