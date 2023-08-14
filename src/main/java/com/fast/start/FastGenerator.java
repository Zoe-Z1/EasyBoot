package com.fast.start;

import com.fast.start.common.generator.config.GeneratorConfig;
import com.fast.start.common.generator.execute.GeneratorExecute;

/**
 * @author zoe
 * @date 2023/7/23
 * @description 代码生成
 */
public class FastGenerator {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/fast?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        GeneratorConfig config = new GeneratorConfig();
        GeneratorExecute.execute(config);
    }
}
