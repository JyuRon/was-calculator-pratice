package org.example.frontcontroller.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View{
    private final String name;

    public JspView(String name) {
        this.name = name;
    }


    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        model.forEach(request::setAttribute);
        // 얻어온 view명을 가지고 동적 페이지를 생성한다.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(name);

        // 생성된 동적페이지를 사용자에게 전달한다
        requestDispatcher.forward(request, response);

    }
}
