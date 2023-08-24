package com.easy.boot.common.generator.execute;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.GeneratorConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.DbManager;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.generator.db.Table;
import com.easy.boot.common.generator.template.AbstractTemplate;
import com.easy.boot.exception.GeneratorException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

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
     * 模板配置
     */
    private final TemplateConfig templateConfig;

    /**
     * 要生存的表信息
     */
    private List<Table> tables;

    private GeneratorExecute(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.globalConfig = generatorConfig.getGlobalConfig();
        this.templateConfig = generatorConfig.getTemplateConfig();
    }

    /**
     * 初始化类
     *
     * @param generatorConfig 生成配置
     * @return GeneratorExecute
     */
    public static GeneratorExecute init(GeneratorConfig generatorConfig) {
        return new GeneratorExecute(generatorConfig);
    }

    /**
     * 设置要生成的表信息
     * @param tableNames 表名
     * @return GeneratorExecute
     */
    public GeneratorExecute tables(String... tableNames) {
        this.tables = Arrays.stream(tableNames).map(Table::new).collect(Collectors.toList());
        return this;
    }

    /**
     * 设置要生成的表信息
     * @param tables 表信息
     * @return GeneratorExecute
     */
    public GeneratorExecute tables(Table... tables) {
        this.tables = Arrays.asList(tables);
        return this;
    }

    /**
     * 执行代码生成
     */
    public void execute() {
        if (CollUtil.isEmpty(tables)) {
            throw new GeneratorException("需要生成的表不能为空");
        }
        // 获取要生成的所有表的信息
        List<MetaTable> metaTables = DbManager.init(generatorConfig.getDataSourceConfig()).getTables(tables);
        // 获取所有的模板
        List<AbstractTemplate> templates = templateConfig.getTemplates();
        // 未找到表或未找到模板类，直接结束
        if (templates.isEmpty() || metaTables.isEmpty()) {
            String str = templates.isEmpty() ? "模板" : "表";
            log.warn("未找到需要生成的" + str);
            return;
        }
        // 遍历表
        eachMetaTables(templates, metaTables);
        // 打开生成目录
        if (globalConfig.getIsOpen()) {
            openPath();
        }
    }

    /**
     * 遍历表，生成代码
     * @param templates 模板列表
     * @param metaTables 表信息列表
     */
    private void eachMetaTables(List<AbstractTemplate> templates, List<MetaTable> metaTables) {
        for (MetaTable metaTable : metaTables) {
            DataMap dataMap = getDataMap(metaTable);
            eachTemplates(templates, dataMap);
        }
    }

    /**
     * 获取配置数据map
     * @param metaTable 表数据信息
     * @return DataMap
     */
    private DataMap getDataMap(MetaTable metaTable) {
        DataMap dataMap = new DataMap();
        dataMap.put(GenConstant.DATA_MAP_KEY_TABLE, metaTable);
        dataMap.put(GenConstant.DATA_MAP_KEY_CONFIG, generatorConfig);
        dataMap.put(GenConstant.DATA_MAP_KEY_ANNOTATION, generatorConfig.getAnnotationConfig());
        dataMap.put(GenConstant.DATA_MAP_KEY_GLOBAL, generatorConfig.getGlobalConfig());
        dataMap.put(GenConstant.DATA_MAP_KEY_TEMPLATE, generatorConfig.getTemplateConfig());
        dataMap.put("date", DateUtil.format(new Date(), globalConfig.getCommentDateFormat()));
        return dataMap;
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
                log.error(buildDataMap.get("fileName") + "代码生成出错,  e -> ", e);
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
        configuration.setDirectoryForTemplateLoading(new File(globalConfig.getTemplateRootPath()));
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 加载模版文件
        Template easyTemplate = configuration.getTemplate(String.valueOf(dataMap.get("templateName")));
        // 生成数据
        String fileName = String.valueOf(dataMap.get("fileName"));
        File file = new File(String.valueOf(dataMap.get("genPath")), fileName);
        boolean isOverride = (boolean) dataMap.get("isOverride");
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
