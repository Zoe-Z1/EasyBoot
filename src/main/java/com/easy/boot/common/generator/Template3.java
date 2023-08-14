package com.easy.boot.common.generator;

import lombok.NoArgsConstructor;

/**
 * @author zoe
 * @date 2023/8/13
 * @description
 */
@NoArgsConstructor
public class Template3 implements EasyTemplate {

    @Override
    public void generator() {
        System.out.println("3333 = " + 3333);
    }
}
