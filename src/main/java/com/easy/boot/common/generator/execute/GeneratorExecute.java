package com.easy.boot.common.generator.execute;

import com.easy.boot.common.generator.EasyTemplate;
import com.easy.boot.common.generator.config.GeneratorConfig;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author zoe
 * @date 2023/8/13
 * @description 代码生成执行器
 */
public class GeneratorExecute {

    /**
     * 执行代码生成
     * @throws Exception
     */
    public static void execute(GeneratorConfig config) throws Exception {
        Reflections reflections = new Reflections(EasyTemplate.class.getPackage().getName());
        Set<Class<? extends EasyTemplate>> implementingClasses = reflections.getSubTypesOf(EasyTemplate.class);

        for (Class<?> implementingClass : implementingClasses) {
            System.out.println("implementingClass = " + implementingClass);
            Object obj = implementingClass.getDeclaredConstructor().newInstance();
            Class<?> c = obj.getClass();
            c.getMethod("generator").invoke(obj);
        }
    }
}
