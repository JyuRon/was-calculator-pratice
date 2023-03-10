package org.example.frontcontroller.mvc;

import org.example.frontcontroller.mvc.adapter.AnnotationHandlerAdapter;
import org.example.frontcontroller.mvc.adapter.HandlerAdapter;
import org.example.frontcontroller.mvc.adapter.SimpleControllerHandlerAdapter;
import org.example.frontcontroller.mvc.common.HandlerKey;
import org.example.frontcontroller.mvc.common.RequestMethod;
import org.example.frontcontroller.mvc.mapper.AnnotationHandlerMapping;
import org.example.frontcontroller.mvc.mapper.HandlerMapping;
import org.example.frontcontroller.mvc.mapper.RequestMappingHandlerMapping;
import org.example.frontcontroller.mvc.view.JspViewResolver;
import org.example.frontcontroller.mvc.view.ModelAndView;
import org.example.frontcontroller.mvc.view.View;
import org.example.frontcontroller.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    private List<HandlerAdapter> handlerAdapters;

    private List<ViewResolver> viewResolvers;

    @Override
    public void init() throws ServletException {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("org.example");
        requestMappingHandlerMapping.init();
        annotationHandlerMapping.init();

        handlerMappings = List.of(requestMappingHandlerMapping, annotationHandlerMapping);
        handlerAdapters = List.of(new SimpleControllerHandlerAdapter(), new AnnotationHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("DispatcherServletService");
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        try {
            // Handler Mapping ??? ?????? ?????? url, method ????????? ?????? Controller(handler) ??????
            Object handler = handlerMappings.stream()
                    .filter(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)) != null)
                    .map(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No handler for [" + requestMethod + ", " + requestURI));


            // Controller(handler) ??? ??????????????? Handler Adapter ??????
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            // view name?????? handler adapter??? ?????? ?????????
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            // UserCreateController ??? return?????? ?????? redirect:/users  --> ?????? ??????
            // redirect or forward ??? ???????????? ?????? ?????? --> viewResolver ??? ???????????? ?????? ??????
            for (ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);
            }

        } catch (Exception e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
