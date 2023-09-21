package com.easy.boot.common.generator.template;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
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
        return buildDataMap;
    }

}
