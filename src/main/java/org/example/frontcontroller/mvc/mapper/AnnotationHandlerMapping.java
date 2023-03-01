package org.example.frontcontroller.mvc.mapper;

import org.example.frontcontroller.mvc.common.HandlerKey;
import org.example.frontcontroller.mvc.annotation.Controller;
import org.example.frontcontroller.mvc.annotation.RequestMapping;
import org.example.frontcontroller.mvc.common.RequestMethod;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;
    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage){
        this.basePackage = basePackage;
    }

    public void init(){
        Reflections reflections = new Reflections(basePackage);

        // @Controller 가 붙은 class 가져옴
        Set<Class<?>> clazzWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);
        clazzWithControllerAnnotation.forEach(clazz ->
                        // 해당 클래스에 선언된 메소드 나열
                        Arrays.stream(clazz.getDeclaredMethods()).forEach(declearedMethod ->{
                            // 각 클래스별 메소드를 순회하며 특정 에노테이션이 붙은걸 탐색
                            RequestMapping requestMapping = declearedMethod.getDeclaredAnnotation(RequestMapping.class);
                            // 각 Request Method 별로 handler에 put
                            Arrays.stream(getRequestMethods(requestMapping))
                                    .forEach(requestMethod -> handlers.put(
                                            new HandlerKey(requestMethod, requestMapping.value()),new AnnotationHandler(clazz,declearedMethod)
                                    ));
                        })
                );
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
