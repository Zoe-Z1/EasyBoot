package cn.easy.boot.common.generator.template;

import cn.easy.boot.common.generator.DataMap;
import cn.easy.boot.common.generator.GenConstant;
import cn.easy.boot.common.generator.config.GlobalConfig;
import cn.easy.boot.common.generator.db.MetaTable;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 模板参数配置抽象类
 */
public abstract class AbstractTemplate {

    /**
     * 备注
     * @param tableRemarks 表备注
     * @return
     */
    protected String getRemarks(String tableRemarks) {
        return tableRemarks;
    };

    /**
     * 模块名
     */
    protected abstract String getModuleName();

    /**
     * 父类class
     */
    protected Class<?> getSuperClass() {
        return null;
    };

    /**
     * 模板文件名
     */
    protected abstract String getTemplateName();
    /**
     * 模板类型
     */
    protected String getTemplateType() {
        return GenConstant.TEMPLATE_TYPE_JAVA;
    }

    /**
     * 生成文件名
     * @param javaName 实体类Java名称
     */
    protected abstract String getFileName(String javaName);

    /**
     * 是否开启代码生成
     * 子类重写该方法即可实现单独控制子类模板生成
     */
    public Boolean isEnable() {
        return true;
    }

    /**
     * 生成时是否覆盖已有文件，模板单独配置的优先级大于全局
     * 返回null则使用全局配置
     * @return isOverride
     */
    protected Boolean getIsOverride() {
        return null;
    }

    /**
     * 构建模板参数，子类模板可以重写该方法自行构建参数
     * @param dataMap  自定义的参数
     * @return dataMap
     */
    public DataMap buildDataMap(DataMap dataMap) {
        // 这里需要深拷贝一个新对象才能确保不会复用到之前的map中的属性
        DataMap buildDataMap = new DataMap();
        if (MapUtil.isNotEmpty(dataMap)) {
            buildDataMap.putAll(dataMap);
        }
        MetaTable metaTable = dataMap.getMetaTable();
        GlobalConfig global = dataMap.getGlobalConfig();
        String pkg = String.join(".", global.getPackageName(), metaTable.getModuleName(), getModuleName());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKG, pkg);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_MODULE_NAME, getModuleName());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_CLASS, getSuperClass());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_FILE_NAME, getFileName(metaTable.getBeanName()));
        buildDataMap.put(GenConstant.DATA_MAP_KEY_TEMPLATE_NAME, getTemplateName());
        Boolean isOverride = getIsOverride();
        if (isOverride == null) {
            isOverride = global.getIsOverride();
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_IS_OVERRIDE, isOverride);
        String remarks = getRemarks(metaTable.getRemarks());
        buildDataMap.put(GenConstant.DATA_MAP_KEY_REMARKS, remarks);
        String templateType = getTemplateType();
        if (StrUtil.isEmpty(templateType) || templateType.equals(GenConstant.TEMPLATE_TYPE_JAVA)) {
            String genPath = String.join("/", global.getOutputPath(), metaTable.getModuleName(), getModuleName());
            genPath = genPath.replaceAll("\\.", "/");
            String zipPath = String.join("/", global.getAuthor(), metaTable.getModuleName(), getModuleName());
            zipPath = zipPath.replaceAll("\\.", "/");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_GEN_PATH, genPath);
            buildDataMap.put(GenConstant.DATA_MAP_KEY_ZIP_PATH, zipPath);
        }
        return buildDataMap;
    }

}
