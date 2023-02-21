package org.example.reflection;

import org.example.reflection.annotation.Controller;
import org.example.reflection.annotation.Service;
import org.example.reflection.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

// @Controller 애노테이션이 설정돼 있는 모든 클래스를 찾아서 출력한다.
class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    @DisplayName("")
    @Test
    void controllerScan(){
        Set<Class<?>> beans = getTypesAnnotationWith(List.of(Controller.class, Service.class));

        logger.debug("beans: [{}]", beans);
    }


    private static Set<Class<?>> getTypesAnnotationWith(List<Class<? extends Annotation>> annotations) {
        // 탐색 범위 지정
        Reflections reflections = new Reflections("org.example");

        // Set<Class ? extends Object>> 의 줄임 표현
        // 어떤 자료형의 객체도 매개변수로 받겠다는 의미
        // 와일드 카드 제네릭
        Set<Class<?>> beans = new HashSet<>();

        // @Controller 어노테이션이 달려있는 대상을 탐색하여 저장
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        return beans;
    }

    @DisplayName("")
    @Test
    void showClass(){
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        //User에 선언된 모든 필드 출력
        logger.debug("User all declared fields [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructors [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared methods [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }

    @DisplayName("")
    @Test
    void load() throws ClassNotFoundException {
        // 방법 1
        Class<User> clazz = User.class;

        // 방법2
        User user = new User("jyuka", "wjy");
        Class<? extends User> clazz2 = user.getClass();

        // 방법3
        Class<?> clazz3 = Class.forName("org.example.reflection.model.User");

        logger.debug("clazz1: [{}]", clazz);
        logger.debug("clazz2: [{}]", clazz2);
        logger.debug("clazz3: [{}]", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz == clazz3).isTrue();
        assertThat(clazz3 == clazz2).isTrue();
    }
}