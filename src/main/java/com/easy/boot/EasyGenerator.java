package com.easy.boot;

import cn.hutool.core.collection.CollUtil;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.*;
import com.easy.boot.common.generator.db.convert.EasyColumnConvertHandler;
import com.easy.boot.common.generator.execute.GeneratorExecute;
import com.easy.boot.common.generator.template.EntityTemplate;
import com.easy.boot.common.generator.template.UpdateDTOTemplate;
import com.easy.boot.common.generator.template.VOTemplate;
import lombok.extern.slf4j.Slf4j;

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
                                        .packageName(GenConstant.DEFAULT_PACKAGE_NAME)
                                        .requestMappingPrefix("/admin")
                                        .outputPath("/Users/zoe/Downloads/template/")
                                        .isOverride(true)
                                        .isOpen(true)
                                        .author("zoe")
                                        .commentDateFormat("yyyy/MM/dd")
                                        .enableImport(true)
                                        .enableExport(true)
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
                                        .enableLog(true)
                                        .enableBuilder(true)
                                        .build())
                        .template(
                                TemplateConfig.builder()
                                        .entity(
                                                EntityTemplate.builder()
                                                        .enableTableField(true)
                                                        .build()
                                        )
                                        .updateDTO(
                                                UpdateDTOTemplate.builder()
                                                        .enableExtendsCreateDTO(true)
                                                        .includeField(CollUtil.newHashSet("id"))
                                                        .build()
                                        )
                                        .vo(
                                                VOTemplate.builder()
                                                        .enable(true)
                                                        .build()
                                        )
                                        .build())
                        .filter(
                                FilterConfig.builder()
                                        .excludeTablePrefix(CollUtil.newHashSet("generate_"))
                                        .excludeField(CollUtil.newHashSet("id","createBy","createTime","updateBy","updateTime","isDel"))
                                        .build()
                        )
                        .build()
        )
                .tables("generate_config")
                .execute();

    }
}
