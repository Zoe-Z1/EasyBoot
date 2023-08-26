package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.GeneratorConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.MetaTable;
import lombok.*;
import org.apache.ibatis.annotations.Mapper;

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
public class MapperTemplate extends AbstractTemplate {

    private String moduleName;

    public Class<?> superClass;

    public String templateName;

    public String fileName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = "mapper";
        }
        return moduleName;
    }

    @Override
    public Class<?> getSuperClass() {
        return BaseMapper.class;
    }

    @Override
    public String getTemplateName() {
        return "mapper.ftl";
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.MAPPER + GenConstant.SUFFIX;
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
        TemplateConfig template = (TemplateConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_TEMPLATE);
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String javaName = metaTable.getBeanName();
        String className = template.getMapper().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityName = template.getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put("className", className);
        buildDataMap.put("entityName", entityName);
    }

    /**
     * 构建父类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildSuperClassName(DataMap buildDataMap) {
        GeneratorConfig generator = (GeneratorConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_CONFIG);
        TemplateConfig template = generator.getTemplateConfig();
        if (template.getMapper().getSuperClass() != null) {
            buildDataMap.put("superName", template.getMapper().getSuperClass().getName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = (GlobalConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_GLOBAL);
        TemplateConfig template = (TemplateConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_TEMPLATE);
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String pkg = String.join(".", global.getPackageName(), metaTable.getModuleName());
        Set<String> pkgs = new HashSet<>();
        if (template.getMapper().getSuperClass() != null) {
            pkgs.add(template.getMapper().getSuperClass().getName());
        }
        pkgs.add(Mapper.class.getName());
        String entityPkg = String.join(".", pkg, template.getEntity().getModuleName(), String.valueOf(buildDataMap.get("entityName")));
        pkgs.add(entityPkg);
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put("pkgs", list);
        pkg = String.join(".",pkg, template.getMapper().getModuleName());
        buildDataMap.put("pkg", pkg);
    }
}
