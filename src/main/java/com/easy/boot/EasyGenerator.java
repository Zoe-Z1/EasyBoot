package com.easy.boot;

import com.easy.boot.common.generator.config.*;
import com.easy.boot.common.generator.db.convert.EasyColumnConvertHandler;
import com.easy.boot.common.generator.execute.GeneratorExecute;
import com.easy.boot.common.generator.template.ControllerTemplate;
import com.easy.boot.common.generator.template.TestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author zoe
 * @date 2023/7/23
 * @description 代码生成
 */
@Slf4j
public class EasyGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/easyboot?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        GeneratorExecute.init(
                GeneratorConfig.builder()
                        .global(
                                GlobalConfig.builder()
                                        .packageName("com.easy.boot")
                                        .templateRootPath(System.getProperty("user.dir") + "/src/main/resources/templates")
                                        .outputPath("/Users/zoe/Downloads/template/")
                                        .isOverride(true)
                                        .isOpen(false)
                                        .enableComment(true)
                                        .author("zoe")
                                        .commentDateFormat("yyyy/MM/dd")
                                        .build()
                        )
                        .dataSource(
                                DataSourceConfig.builder()
                                        .url(url)
                                        .username("root")
                                        .password("zz123456")
                                        .columnConvertHandler(new EasyColumnConvertHandler())
                                        .build()
                        )
                        .annotation(
                                AnnotationConfig.builder()
                                        .enableSwagger(true)
                                        .enableLog(true)
                                        .enableBuilder(true)
                                        .build())
                        .template(
                                TemplateConfig.builder()
                                        .controller(ControllerTemplate.builder().className("Aaaaa").build())
                                        .addTemplate(Arrays.asList(new TestTemplate()))
                                        .build())
                        .build()
        )
                .tables("login_log")
                .execute();

    }
}
