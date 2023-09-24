package com.easy.boot.common.generator.template;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.easy.boot.admin.dataDict.entity.DataDict;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.DictSql;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.saToken.UserContext;
import com.easy.boot.utils.JsonUtil;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zoe
 * @date 2023/9/23
 * @description sql模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SqlTemplate extends AbstractTemplate {

    private String remarks;

    private String moduleName;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

    private Boolean execute;

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
            moduleName = GenConstant.SQL_PACKAGE_NAME;
        }
        return moduleName;
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.SQL_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SQL_SUFFIX;
        }
        String camelName = NamingCase.toUnderlineCase(javaName);
        return camelName + GenConstant.SQL_SUFFIX;
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

    public Boolean getExecute() {
        if (execute == null) {
            execute = false;
        }
        return execute;
    }

    @Override
    public DataMap buildDataMap(DataMap dataMap) {
        DataMap buildDataMap = super.buildDataMap(dataMap);
        // 构建生成参数
        buildGenParam(buildDataMap);
        // 处理实体类属性
        handleField(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建生成参数
     * @param buildDataMap
     */
    private void buildGenParam(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        String permission = metaTable.getName().replaceAll("_", ":");
        if (StrUtil.isNotEmpty(metaTable.getUiModuleName())) {
            permission = String.join(":", metaTable.getUiModuleName(), permission);
        }
        String kebabCase = NamingCase.toKebabCase(metaTable.getCamelName());
        String menuPath = "/" + metaTable.getUiModuleName() + "/" + kebabCase;
        String component = GenConstant.VUE_PACKAGE_NAME + menuPath;
        Long createBy = UserContext.getId();
        String createUsername = UserContext.getUsername();
        Long createTime = DateUtil.current();

        buildDataMap.put(GenConstant.DATA_MAP_KEY_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PAGE_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_DETAIL_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CREATE_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_UPDATE_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_DEL_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_BATCH_DEL_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_IMPORT_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_DOWNLOAD_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_EXPORT_MENU_ID, IdWorker.getId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PARENT_MENU_ID, metaTable.getParentMenuId());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PERMISSION, permission);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_MENU_PATH, menuPath);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_COMPONENT, component);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CREATE_BY, createBy);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CREATE_USERNAME, createUsername);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CREATE_TIME, createTime);
    }

    /**
     * 处理实体类属性
     * @param buildDataMap 已构建过的参数
     */
    private void handleField(DataMap buildDataMap) {
        FilterConfig filter = buildDataMap.getFilterConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        // 不要直接获取处理，会导致其他地方没有数据
        List<GenerateColumn> columns = JsonUtil.copyList(metaTable.getColumns(), GenerateColumn.class);
        columns.removeIf(item -> filter.getExcludeField().contains(item.getJavaName()));

        List<DictSql> dictSqls = new ArrayList<>();
        for (GenerateColumn column : columns) {
            if (column.getIsCreate()) {
                List<DataDict> list = new ArrayList<>();
                if (StrUtil.isEmpty(column.getColumnRemarks())) {
                    continue;
                }
                int index = column.getColumnRemarks().indexOf("#");
                if (index < 0) {
                    continue;
                }
                Long domainId = IdWorker.getId();
                // 解析注释，仅支持 #A：xxx，B：XXX 格式
                String dictStr = column.getColumnRemarks().substring(index + 1);
                dictStr = dictStr.replaceAll("，", ",");
                dictStr = dictStr.replaceAll("：", ":");
                String[] dictArray = dictStr.split(",");
                for (String dict : dictArray) {
                    String[] array = dict.split(":");
                    if (array.length == 2) {
                        DataDict dataDict = DataDict.builder()
                                .id(IdWorker.getId())
                                .domainId(domainId)
                                .code(array[0].trim())
                                .label(array[1].trim())
                                .build();
                        list.add(dataDict);
                    }
                }
                if (list.isEmpty()) {
                    continue;
                }
                String domainCode = column.getDictDomainCode();
                if (StrUtil.isEmpty(domainCode)) {
                    domainCode = metaTable.getName() + "_" + column.getColumnName();
                }
                DictSql dictSql = DictSql.builder()
                        .domainId(domainId)
                        .code(domainCode)
                        .name(column.getColumnRemarks().substring(0, index).trim())
                        .remarks(column.getColumnRemarks().trim())
                        .list(list)
                        .build();
                dictSqls.add(dictSql);
            }
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_DICT_SQLS, dictSqls);
    }

}
