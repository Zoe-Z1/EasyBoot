package com.fast.start.common.generator.execute;

import com.fast.start.common.generator.FastTemplate;
import com.fast.start.common.generator.config.GeneratorConfig;
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
        Reflections reflections = new Reflections(FastTemplate.class.getPackage().getName());
        Set<Class<? extends FastTemplate>> implementingClasses = reflections.getSubTypesOf(FastTemplate.class);

        for (Class<?> implementingClass : implementingClasses) {
            System.out.println("implementingClass = " + implementingClass);
            Object obj = implementingClass.getDeclaredConstructor().newInstance();
            Class<?> c = obj.getClass();
            c.getMethod("generator").invoke(obj);
        }
    }
}
