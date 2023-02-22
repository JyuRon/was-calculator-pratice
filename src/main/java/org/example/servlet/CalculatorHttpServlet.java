package org.example.servlet;

import org.example.calc.Calculator;
import org.example.calc.newCalc.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//해당 경로로 들어오는 Url에 대한 설
@WebServlet("/calculate")
public class CalculatorHttpServlet extends HttpServlet {

    // servlet은 싱글톤으로 관리됨, 이를 확인하기 위한 로깅처리
    private static final Logger log = LoggerFactory.getLogger(CalculatorHttpServlet.class);
    private ServletConfig servletConfig;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("service : calculator");
        int operand1 = Integer.parseInt(request.getParameter("operand1"));
        String operator = request.getParameter("operator");
        int operand2 = Integer.parseInt(request.getParameter("operand2"));

        int result = Calculator.calculate(new PositiveNumber(operand1),operator,new PositiveNumber(operand2));

        PrintWriter writer = response.getWriter();
        writer.println(result);
    }
}
