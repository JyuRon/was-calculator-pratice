package org.example.di.controller;

import org.example.di.annotation.Controller;
import org.example.di.annotation.Inject;
import org.example.di.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
