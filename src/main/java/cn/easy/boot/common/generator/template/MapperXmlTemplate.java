package cn.easy.boot.common.generator.template;

import cn.easy.boot.admin.generateColumn.entity.GenerateColumn;
import cn.easy.boot.common.generator.DataMap;
import cn.easy.boot.common.generator.GenConstant;
import cn.easy.boot.common.generator.config.GlobalConfig;
import cn.easy.boot.common.generator.config.TemplateConfig;
import cn.easy.boot.common.generator.db.MetaTable;
import cn.hutool.core.util.StrUtil;
import cn.easy.boot.utils.JsonUtil;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/8/31
 * @description mapper.xml模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MapperXmlTemplate extends AbstractTemplate {

    private String moduleName;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

    /**
     * 生成BaseResultMap映射
     */
    private Boolean genBaseResultMap;

    /**
     * 生成baseColumnList
     */
    private Boolean genBaseColumnList;

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = GenConstant.MODULE_MAPPER_XML;
        }
        return moduleName;
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.MAPPER_XML_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.XML_SUFFIX;
        }
        return javaName + GenConstant.MAPPER + GenConstant.XML_SUFFIX;
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

    public Boolean getGenBaseResultMap() {
        if (genBaseResultMap == null) {
            genBaseResultMap = true;
        }
        return genBaseResultMap;
    }

    public Boolean getGenBaseColumnList() {
        if (genBaseColumnList == null) {
            genBaseColumnList = true;
        }
        return genBaseColumnList;
    }

    @Override
    public DataMap buildDataMap(DataMap dataMap) {
        DataMap buildDataMap = super.buildDataMap(dataMap);
        // 构建packageName
        buildPkgName(buildDataMap);
        // 构建其他参数
        buildOther(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建packageName
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgName(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        GlobalConfig global = buildDataMap.getGlobalConfig();
        MapperTemplate mapper = template.getMapper();
        EntityTemplate entity = template.getEntity();
        String javaName = metaTable.getBeanName();
        String mapperName = mapper.getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityName = entity.getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String pkgName = String.join(".", global.getPackageName(), metaTable.getModuleName());
        String mapperPkgName = String.join(".", pkgName, mapper.getModuleName(), mapperName);
        String entityPkgName = String.join(".", pkgName, entity.getModuleName(), entityName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_MAPPER_PKG_NAME, mapperPkgName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_PKG_NAME, entityPkgName);
    }

    /**
     * 构建其他参数
     * @param buildDataMap 已构建过的参数
     */
    private void buildOther(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        // 不要直接获取处理，会导致其他地方没有数据
        List<GenerateColumn> columns = JsonUtil.copyList(metaTable.getColumns(), GenerateColumn.class);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_COLUMNS, columns);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_GEN_BASE_RESULT_MAP_NAME, getGenBaseResultMap());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_GEN_BASE_COLUMN_LIST_NAME, getGenBaseColumnList());
        if (getGenBaseColumnList()) {
            String baseColumnList = columns.stream().map(GenerateColumn::getColumnName).collect(Collectors.joining(", "));
            buildDataMap.put(GenConstant.DATA_MAP_KEY_BASE_COLUMN_LIST_NAME, baseColumnList);
        }
    }

}
