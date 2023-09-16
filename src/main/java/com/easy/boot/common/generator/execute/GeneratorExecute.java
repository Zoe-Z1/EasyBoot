package com.easy.boot.common.generator.execute;

import cn.hutool.core.collection.CollUtil;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.*;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.generator.template.AbstractTemplate;
import com.easy.boot.exception.GeneratorException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zoe
 * @date 2023/8/13
 * @description 代码生成执行器
 */
@Slf4j
public class GeneratorExecute {

    /**
     * 代码生成配置
     */
    private final GeneratorConfig generatorConfig;

    /**
     * 全局配置
     */
    private final GlobalConfig globalConfig;

    /**
     * 数据源配置
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * 注解配置
     */
    private final AnnotationConfig annotationConfig;

    /**
     * 模板配置
     */
    private final TemplateConfig templateConfig;

    /**
     * 过滤配置
     */
    private final FilterConfig filterConfig;

    /**
     * 要生成的列信息
     */
    private List<GenerateColumn> columns;

    private GeneratorExecute(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.globalConfig = generatorConfig.getGlobalConfig();
        this.dataSourceConfig = generatorConfig.getDataSourceConfig();
        this.annotationConfig = generatorConfig.getAnnotationConfig();
        this.templateConfig = generatorConfig.getTemplateConfig();
        this.filterConfig = generatorConfig.getFilterConfig();
    }

    /**
     * 初始化类
     *
     * @param generateConfig 生成参数配置
     * @return MainGeneratorExecute
     */
    public static GeneratorExecute init(GenerateConfig generateConfig) {
        GeneratorConfig generatorConfig = GeneratorConfig
                .builder()
                .build();
        return new GeneratorExecute(generatorConfig);
    }

    /**
     * 设置要生成的列信息
     * @param columns 要生成的列
     * @return GeneratorExecute
     */
    public GeneratorExecute columns(List<GenerateColumn> columns) {
        this.columns = columns;
        return this;
    }

    /**
     * 执行代码生成
     */
    public void execute() {
        if (CollUtil.isEmpty(columns)) {
            throw new GeneratorException("需要生成的列不能为空");
        }
        // 获取所有的模板
        List<AbstractTemplate> templates = templateConfig.getTemplates();
        // 未找到模板类，直接结束
        if (templates.isEmpty()) {
            log.warn("未找到需要生成的模板");
            return;
        }
        DataMap dataMap = DataMap.getAndPutDataMap(generatorConfig);
        dataMap.put(GenConstant.DATA_MAP_KEY_TABLE, columns);
        // 遍历生成
//        eachTemplates(templates, columns);
//        // 打开生成目录
//        if (globalConfig.getIsOpen()) {
//            openPath();
//        }
    }

    /**
     * 遍历表，生成代码
     * @param templates 模板列表
     * @param metaTables 表信息列表
     */
    private void eachMetaTables(List<AbstractTemplate> templates, List<MetaTable> metaTables) {
        for (MetaTable metaTable : metaTables) {
            DataMap dataMap = DataMap.getAndPutDataMap(generatorConfig);
            dataMap.put(GenConstant.DATA_MAP_KEY_TABLE, metaTable);
            eachTemplates(templates, dataMap);
        }
    }

    /**
     * 遍历模板，生成代码
     * @param templates 模板列表
     * @param dataMap 配置数据map
     */
    private void eachTemplates(List<AbstractTemplate> templates, DataMap dataMap) {
        for (AbstractTemplate template : templates) {
            // 创建数据模型
            DataMap buildDataMap = template.buildDataMap(dataMap);
            if (!template.isEnable()) {
                log.info(buildDataMap.get("fileName") + " 已跳过代码生成!");
                continue;
            }
            try {
                this.generator(buildDataMap);
            } catch (Exception e) {
                log.error(buildDataMap.getString(GenConstant.DATA_MAP_KEY_FILE_NAME) + "代码生成出错,  e -> ", e);
            }
            buildDataMap.clear();
        }
    }

    /**
     * 生成代码
     *
     * @param dataMap 代码生成参数
     * @throws Exception
     */
    private void generator(DataMap dataMap) throws Exception {
        // 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 获取模版路径
        configuration.setDirectoryForTemplateLoading(new File(templateConfig.getTemplateRootPath()));
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 加载模版文件
        Template easyTemplate = configuration.getTemplate(dataMap.getString(GenConstant.DATA_MAP_KEY_TEMPLATE_NAME));
        // 生成数据
        String fileName = dataMap.getString(GenConstant.DATA_MAP_KEY_FILE_NAME);
        File file = new File(dataMap.getString(GenConstant.DATA_MAP_KEY_GEN_PATH), fileName);
        boolean isOverride = (boolean) dataMap.get(GenConstant.DATA_MAP_KEY_IS_OVERRIDE);
        // 文件存在时根据配置不覆盖代码
        if (file.exists() && !isOverride) {
            log.info(fileName + " 已存在，已根据配置不覆盖!");
            return;
        }
        // 目录不存在则创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        // 输出文件
        easyTemplate.process(dataMap, out);
        //关闭流
        out.flush();
        out.close();
        log.info(fileName + " 生成成功!");
    }

    /**
     * 打开目录
     */
    private void openPath() {
        try {
            Desktop.getDesktop().open(new File(globalConfig.getOutputPath()));
        } catch (IOException e) {
            log.error("打开目录 {} 失败 e -> ", globalConfig.getOutputPath(), e);
        }
    }
}
