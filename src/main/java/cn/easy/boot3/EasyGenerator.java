package cn.easy.boot3;

import cn.easy.boot3.common.generator.GenConstant;
import cn.easy.boot3.common.generator.config.*;
import cn.hutool.core.collection.CollUtil;
import cn.easy.boot3.common.generator.config.*;
import cn.easy.boot3.common.generator.execute.GeneratorExecute;
import cn.easy.boot3.common.generator.template.EntityTemplate;
import cn.easy.boot3.common.generator.template.SqlTemplate;
import cn.easy.boot3.common.generator.template.UpdateDTOTemplate;
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
                                        .outputPath("/Users/zoe/Downloads/code/")
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
                                        .sql(
                                                SqlTemplate.builder()
                                                        .execute(false)
                                                        .build()
                                        )
                                        .build())
                        .filter(
                                FilterConfig.builder()
                                        .excludeTablePrefix(CollUtil.newHashSet("generate_"))
                                        .excludeField(CollUtil.newHashSet("id","createBy","createUsername","createTime","updateBy","updateUsername","updateTime","isDel"))
                                        .build()
                        )
                        .build()
        )
                .tables("login_log")
                .execute();

    }
}
