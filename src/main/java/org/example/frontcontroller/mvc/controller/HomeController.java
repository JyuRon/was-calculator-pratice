package org.example.frontcontroller.mvc.controller;

import org.example.frontcontroller.mvc.common.RequestMethod;
import org.example.frontcontroller.mvc.annotation.Controller;
import org.example.frontcontroller.mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "home";
    }
}
