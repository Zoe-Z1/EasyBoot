package com.easy.boot3.common.generator.template;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.easy.boot3.common.generator.DataMap;
import com.easy.boot3.common.generator.GenConstant;
import com.easy.boot3.common.generator.config.GlobalConfig;
import com.easy.boot3.common.generator.db.MetaTable;
import lombok.*;

/**
 * @author zoe
 * @date 2023/9/17
 * @description Js模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JsTemplate extends AbstractTemplate {

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
        return GenConstant.JS_TEMPLATE_NAME;
    }

    @Override
    protected String getTemplateType() {
        return GenConstant.TEMPLATE_TYPE_VUE2;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.JS_SUFFIX;
        }
        String name = NamingCase.toKebabCase(javaName);
        return name + GenConstant.JS_SUFFIX;
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
        // 构建生成参数
        buildGenParam(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建生成参数
     * @param buildDataMap
     */
    private void buildGenParam(DataMap buildDataMap) {
        MetaTable metaTable = buildDataMap.getMetaTable();
        GlobalConfig global = buildDataMap.getGlobalConfig();
        String genPath = String.join("/", global.getOutputPath(), getModuleName(), GenConstant.JS_PACKAGE_NAME);
        genPath = genPath.replaceAll("\\.", "/");
        String zipPath = String.join("/", global.getAuthor(), getModuleName(), GenConstant.JS_PACKAGE_NAME);
        zipPath = zipPath.replaceAll("\\.", "/");
        if (StrUtil.isNotEmpty(metaTable.getUiModuleName())) {
            genPath = String.join("/", genPath, metaTable.getUiModuleName());
            zipPath = String.join("/", zipPath, metaTable.getUiModuleName());
            buildDataMap.put(GenConstant.DATA_MAP_KEY_UI_MODULE_NAME, metaTable.getUiModuleName());
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_GEN_PATH, genPath);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ZIP_PATH, zipPath);
    }

}
