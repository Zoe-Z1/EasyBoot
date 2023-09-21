package com.easy.boot.common.generator.template;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.utils.JsonUtil;
import lombok.*;

import java.util.List;

/**
 * @author zoe
 * @date 2023/9/17
 * @description index.vue模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndexVueTemplate extends AbstractTemplate {

    private String remarks;

    private String moduleName;

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
            moduleName = GenConstant.MODULE_VUE;
        }
        return moduleName;
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.INDEX_VUE_TEMPLATE_NAME;
    }

    @Override
    protected String getTemplateType() {
        return GenConstant.TEMPLATE_TYPE_VUE2;
    }

    @Override
    protected String getFileName(String javaName) {
        return "index" + GenConstant.VUE_SUFFIX;
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
        return buildDataMap;
    }

    /**
     * 构建类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildClassName(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        String javaName = metaTable.getBeanName();
        String className = NamingCase.toCamelCase(javaName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
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
        columns.removeIf(item -> filter.getExcludeField().contains(item.getJavaName()));
        for (GenerateColumn column : columns) {
            if (column.getJavaName().equals("os") || column.getJavaName().equals("proCode")) {
                column.setDictDomainCode(column.getColumnName() + "code");
            }
        }
        long count = columns.stream().filter(item -> StrUtil.isNotEmpty(item.getDictDomainCode())).count();
        buildDataMap.put(GenConstant.DATA_MAP_KEY_HAS_DICT, count > 0);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_COLUMNS, columns);
        if (global.getEnableExport() || global.getEnableImport()) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_ENABLE_EXCEL, true);
        }
    }

}