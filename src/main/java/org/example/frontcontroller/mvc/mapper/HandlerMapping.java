package org.example.frontcontroller.mvc.mapper;

import org.example.frontcontroller.mvc.common.HandlerKey;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}
