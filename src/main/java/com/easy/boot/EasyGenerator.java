package com.easy.boot;

import com.easy.boot.common.generator.execute.GeneratorExecute;
import com.easy.boot.common.generator.config.GeneratorConfig;

/**
 * @author zoe
 * @date 2023/7/23
 * @description 代码生成
 */
public class EasyGenerator {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/fast?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        GeneratorConfig config = new GeneratorConfig();
        GeneratorExecute.execute(config);
    }
}
