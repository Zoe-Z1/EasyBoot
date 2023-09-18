package com.easy.boot.common.generator.execute;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.admin.generate.entity.GenerateCode;
import com.easy.boot.admin.generate.entity.GeneratePreviewVO;
import com.easy.boot.admin.generateConfig.entity.GenerateTemplate;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.*;
import com.easy.boot.common.generator.db.DbManager;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.generator.db.Table;
import com.easy.boot.common.generator.template.*;
import com.easy.boot.exception.GeneratorException;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.JsonUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    private GeneratorConfig generatorConfig;

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig;

    /**
     * 注解配置
     */
    private AnnotationConfig annotationConfig;

    /**
     * 模板配置
     */
    private TemplateConfig templateConfig;

    /**
     * 过滤配置
     */
    private FilterConfig filterConfig;

    /**
     * 要生成的表信息
     */
    private List<Table> tables;

    /**
     * 要生成的表信息
     */
    private MetaTable metaTable;

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
     * @param generatorConfig 生成配置
     * @return MainGeneratorExecute
     */
    public static GeneratorExecute init(GeneratorConfig generatorConfig) {
        return new GeneratorExecute(generatorConfig);
    }

    /**
     * 初始化类
     *
     * @param generateConfig 生成参数配置
     * @return MainGeneratorExecute
     */
    public static GeneratorExecute init(GenerateConfig generateConfig) {
        GenerateTemplate generateTemplate = JsonUtil.toBean(generateConfig.getTemplateJson(), GenerateTemplate.class);
        Set<String> excludeTablePrefix = new HashSet<>();
        Set<String> excludeTableSuffix = new HashSet<>();
        Set<String> excludeField = new HashSet<>();
        if (StrUtil.isNotEmpty(generateConfig.getExcludeTablePrefix())) {
            excludeTablePrefix = Arrays.stream(generateConfig.getExcludeTablePrefix().split(",")).collect(Collectors.toSet());
        }
        if (StrUtil.isNotEmpty(generateConfig.getExcludeTableSuffix())) {
            excludeTableSuffix = Arrays.stream(generateConfig.getExcludeTableSuffix().split(",")).collect(Collectors.toSet());
        }
        if (StrUtil.isNotEmpty(generateConfig.getExcludeField())) {
            excludeField = Arrays.stream(generateConfig.getExcludeField().split(",")).collect(Collectors.toSet());
        }
        GeneratorConfig generatorConfig = GeneratorConfig.builder()
                .global(
                        GlobalConfig.builder()
                                .packageName(generateConfig.getPackageName())
                                .requestMappingPrefix(generateConfig.getRequestMappingPrefix())
                                .outputPath(generateConfig.getOutputPath())
                                .isOverride(true)
                                .isOpen(generateConfig.getIsOpen() == 0)
                                .author(generateConfig.getAuthor())
                                .commentDateFormat("yyyy/MM/dd")
                                .enableImport(generateConfig.getEnableImport() == 0)
                                .enableExport(generateConfig.getEnableExport() == 0)
                                .build()
                )
                .dataSource(new DataSourceConfig())
                .annotation(
                        AnnotationConfig.builder()
                                .enableLog(generateConfig.getEnableLog() == 0)
                                .enableBuilder(generateConfig.getEnableBuilder() == 0)
                                .build())
                .template(
                        TemplateConfig.builder()
                                .controller(BeanUtil.copyBean(generateTemplate.getController(), ControllerTemplate.class))
                                .service(BeanUtil.copyBean(generateTemplate.getService(), ServiceTemplate.class))
                                .serviceImpl(BeanUtil.copyBean(generateTemplate.getServiceImpl(), ServiceImplTemplate.class))
                                .mapper(BeanUtil.copyBean(generateTemplate.getMapper(), MapperTemplate.class))
                                .xml(BeanUtil.copyBean(generateTemplate.getXml(), MapperXmlTemplate.class))
                                .entity(
                                        EntityTemplate.builder()
                                                .enable(generateTemplate.getEntity().getEnable())
                                                .enableTableField(true)
                                                .build()
                                )
                                .updateDTO(
                                        UpdateDTOTemplate.builder()
                                                .enableExtendsCreateDTO(false)
                                                .enable(generateTemplate.getUpdateDTO().getEnable())
                                                .build()
                                )
                                .createDTO(BeanUtil.copyBean(generateTemplate.getCreateDTO(), CreateDTOTemplate.class))
                                .query(BeanUtil.copyBean(generateTemplate.getQuery(), QueryTemplate.class))
                                .vo(BeanUtil.copyBean(generateTemplate.getVo(), VOTemplate.class))
                                .build())
                .filter(
                        FilterConfig.builder()
                                .excludeTablePrefix(excludeTablePrefix)
                                .excludeTableSuffix(excludeTableSuffix)
                                .excludeField(excludeField)
                                .build()
                )
                .build();
        return new GeneratorExecute(generatorConfig);
    }

    /**
     * 设置要生成的表信息
     * @param tableNames 表名
     * @return MainGeneratorExecute
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
     * 设置要生成的表信息
     * @param metaTable 要生成的表信息
     * @return GeneratorExecute
     */
    public GeneratorExecute metaTable(MetaTable metaTable) {
        this.metaTable = metaTable;
        return this;
    }

    /**
     * 代码生成预览
     */
    public List<GenerateCode> preview() throws Exception {
        if (metaTable == null) {
            throw new GeneratorException("需要生成的表不能为空");
        }
        // 获取所有的模板
        List<AbstractTemplate> templates = templateConfig.getTemplates();
        // 未找到模板类，直接结束
        if (templates.isEmpty()) {
            log.warn("未找到需要生成的模板");
            return new ArrayList<>();
        }
        List<GenerateCode> codes = new ArrayList<>();
        DataMap dataMap = DataMap.getAndPutDataMap(generatorConfig);
        dataMap.put(GenConstant.DATA_MAP_KEY_TABLE, metaTable);
        // 遍历模板
        for (AbstractTemplate template : templates) {
            // 创建数据模型
            DataMap buildDataMap = template.buildDataMap(dataMap);
            if (!template.isEnable()) {
                log.info(buildDataMap.get("fileName") + " 已跳过代码生成!");
                continue;
            }
            String fileName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_FILE_NAME);
            String zipPath = buildDataMap.getString(GenConstant.DATA_MAP_KEY_ZIP_PATH);
            String genPath = String.join("/", zipPath, fileName);
            // 创建freeMarker配置实例
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            // 获取模版路径
            configuration.setClassForTemplateLoading(FreeMarkerTemplateUtils.class, "/templates");
            configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
            // 加载模版文件
            Template easyTemplate = configuration.getTemplate(buildDataMap.getString(GenConstant.DATA_MAP_KEY_TEMPLATE_NAME));
            StringWriter writer = new StringWriter();
            easyTemplate.process(buildDataMap, writer);
            buildDataMap.clear();
            GenerateCode code = GenerateCode.builder()
                    .author(globalConfig.getAuthor())
                    .filename(fileName)
                    .genPath(genPath)
                    .fileContent(writer.toString())
                    .build();
            codes.add(code);
            writer.close();
        }
        return codes;
    }


    /**
     * main方法执行代码生成
     */
    public void execute() {
        if (CollUtil.isEmpty(tables)) {
            throw new GeneratorException("需要生成的表不能为空");
        }
        List<MetaTable> metaTables = DbManager.init(dataSourceConfig, filterConfig).getTables(tables);
        // 获取所有的模板
        List<AbstractTemplate> templates = templateConfig.getTemplates();
        // 未找到模板类，直接结束
        if (templates.isEmpty()) {
            log.warn("未找到需要生成的模板");
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
