package com.easy.boot;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.BaseEntity;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zoe
 * @date 2023/7/23
 * @description 代码生成
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/fast?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        String outputDir = "/Users/zoe/Downloads";
        boolean fileOverride = true;
        FastAutoGenerator.create(url, "root", "zz123456")
                .globalConfig(builder -> {
                    builder.author("zoe") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy/MM/dd")
                            .outputDir(outputDir); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    String xmlPath = outputDir + "/com/easy/boot/resource";
                    builder.parent("com.easy.boot.admin") // 设置父包名
                            .entity("entity")
                            .moduleName("taskLog") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    if (fileOverride) {
                        builder.controllerBuilder().enableFileOverride();
                        builder.entityBuilder().enableFileOverride();
                        builder.serviceBuilder().enableFileOverride();
                        builder.mapperBuilder().enableFileOverride();
                    }
                    builder.controllerBuilder()
                            .enableRestStyle()
                            .superClass(BaseController.class);
                    builder.entityBuilder()
                            .disableSerialVersionUID()
                            .enableFileOverride()
                            .enableLombok()
                            .enableChainModel()
                            .enableTableFieldAnnotation()
                            .addSuperEntityColumns("id", "create_by", "create_time", "update_by", "update_time", "is_del")
                            .superClass(BaseEntity.class);
                    builder.mapperBuilder()
                            .enableBaseResultMap();
                    builder.addInclude("task_log") // 设置需要生成的表名
                            .addTablePrefix("admin_", "mobile_"); // 设置过滤表前缀
                })
                .injectionConfig(consumer -> {
                    consumer.beforeOutputFile((tableInfo, objectMap) -> {
                        System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                    });
                    // 自定义模板
                    CustomFile customFile = new CustomFile.Builder()
                            .fileName("Query.java")
                            .templatePath("/templates/entityQuery.java.ftl")
                            .packageName("entity")
                            .build();
                    if (fileOverride) {
                        customFile = new CustomFile.Builder()
                                .enableFileOverride()
                                .fileName("Query.java")
                                .templatePath("/templates/entityQuery.java.ftl")
                                .packageName("entity")
                                .build();
                    }
                    consumer.customFile(customFile);

                    customFile = new CustomFile.Builder()
                            .fileName("CreateDTO.java")
                            .templatePath("/templates/entityCreateDTO.java.ftl")
                            .packageName("entity")
                            .build();
                    if (fileOverride) {
                        customFile = new CustomFile.Builder()
                                .enableFileOverride()
                                .fileName("CreateDTO.java")
                                .templatePath("/templates/entityCreateDTO.java.ftl")
                                .packageName("entity")
                                .build();
                    }
                    consumer.customFile(customFile);

                    customFile = new CustomFile.Builder()
                            .fileName("UpdateDTO.java")
                            .templatePath("/templates/entityUpdateDTO.java.ftl")
                            .packageName("entity")
                            .build();
                    if (fileOverride) {
                        customFile = new CustomFile.Builder()
                                .enableFileOverride()
                                .fileName("UpdateDTO.java")
                                .templatePath("/templates/entityUpdateDTO.java.ftl")
                                .packageName("entity")
                                .build();
                    }
                    consumer.customFile(customFile);
                    // 自定义参数
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("requestBasePath", "admin");
                    customMap.put("querySuperEntity", "BasePageQuery");
                    customMap.put("querySuperEntityPackage", "com.easy.boot.common.base");
                    consumer.customMap(customMap);
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
