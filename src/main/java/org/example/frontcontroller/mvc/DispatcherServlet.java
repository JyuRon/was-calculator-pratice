package org.example.frontcontroller.mvc;

import org.example.frontcontroller.mvc.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void init() throws ServletException {
        requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("DispatcherServletService");

        Controller handler = requestMappingHandlerMapping.findHandler(request.getRequestURI());

        try {
            // Custom Controller을 통해 view 명을 불러옴
            String viewName = handler.handleRequest(request, response);

            // 얻어온 view명을 가지고 동적 페이지를 생성한다.
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);

            // 생성된 동적페이지를 사용자에게 전달한다
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
