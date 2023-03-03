package org.example.di;

import org.example.di.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanFactoryUtils {
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        // 특정 조건이 포함된 생성자를 가져옴
        Set<Constructor> injectedConstructors = ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Inject.class));
        if(injectedConstructors.isEmpty()){
            return null;
        }
        return injectedConstructors.iterator().next();
    }
}
