package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
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

    private String moduleName;

    private Class<?> superClass;

    private String templateName;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

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
        // 构建父类名
        buildSuperClassName(buildDataMap);
        // 构建需要导入的包
        buildPkgDataMap(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildClassName(DataMap buildDataMap) {
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String javaName = metaTable.getBeanName();
        String className = template.getService().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put("className", className);
    }

    /**
     * 构建父类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildSuperClassName(DataMap buildDataMap) {
        TemplateConfig template = buildDataMap.getTemplateConfig();
        if (template.getService().getSuperClass() != null) {
            buildDataMap.put("superName", template.getService().getSuperClass().getName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        AnnotationConfig annotation = buildDataMap.getAnnotationConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        Set<String> pkgs = new HashSet<>();
        if (template.getService().getSuperClass() != null) {
            pkgs.add(template.getService().getSuperClass().getName());
        }
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }
}
