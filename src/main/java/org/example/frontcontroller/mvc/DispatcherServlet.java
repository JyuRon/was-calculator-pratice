package org.example.frontcontroller.mvc;

import org.example.frontcontroller.mvc.controller.Controller;
import org.example.frontcontroller.mvc.view.JspViewResolver;
import org.example.frontcontroller.mvc.view.ModelAndView;
import org.example.frontcontroller.mvc.view.View;
import org.example.frontcontroller.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;

    private List<HandlerAdapter> handlerAdapters;

    private List<ViewResolver> viewResolvers;

    @Override
    public void init() throws ServletException {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();
        handlerMapping = requestMappingHandlerMapping;

        handlerAdapters = List.of(new SimpleControllerHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("DispatcherServletService");

        try {
            // Handler Mapping 에 의해 요청 url, method 방식에 맞는 Controller(handler) 탐색
            Controller handler = handlerMapping.findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()),request.getRequestURI()));

            // Controller(handler) 가 사용가능한 Handler Adapter 탐색
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            // view name등을 handler adapter를 통해 받아옴
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            // UserCreateController 의 return값의 경우 redirect:/users  --> 오류 발생
            // redirect or forward 도 가능하게 수정 필요 --> viewResolver 의 기능으로 해결 가능
            for (ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(),request, response);
            }

        } catch (Exception e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
