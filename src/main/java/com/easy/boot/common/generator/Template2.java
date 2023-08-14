package com.easy.boot.common.generator;

import lombok.NoArgsConstructor;

/**
 * @author zoe
 * @date 2023/8/13
 * @description
 */
@NoArgsConstructor
public class Template2 implements EasyTemplate {

    @Override
    public void generator() {
        System.out.println("2222 = " + 2222);
    }
}
