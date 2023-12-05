package cn.easy.boot3.common.generator.template;

import cn.easy.boot3.common.generator.DataMap;
import cn.easy.boot3.common.generator.GenConstant;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.easy.boot3.common.generator.config.GlobalConfig;
import cn.easy.boot3.common.generator.config.TemplateConfig;
import cn.easy.boot3.common.generator.db.MetaTable;
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
            moduleName = GenConstant.MODULE_MAPPER;
        }
        return moduleName;
    }

    @Override
    protected Class<?> getSuperClass() {
        if (superClass == null) {
            return BaseMapper.class;
        } else {
            return superClass;
        }
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.MAPPER_TEMPLATE_NAME;
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
        MetaTable metaTable = buildDataMap.getMetaTable();
        String javaName = metaTable.getBeanName();
        String className = template.getMapper().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityName = template.getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_NAME, entityName);
        if (getSuperClass() != null) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getSimpleName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = buildDataMap.getGlobalConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
        String pkg = String.join(".", global.getPackageName(), metaTable.getModuleName());
        Set<String> pkgs = new HashSet<>();
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        pkgs.add(Mapper.class.getName());
        String entityPkg = String.join(".", pkg, template.getEntity().getModuleName(), buildDataMap.getString(GenConstant.DATA_MAP_KEY_ENTITY_NAME));
        pkgs.add(entityPkg);
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }
}
