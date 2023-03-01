package org.example.frontcontroller.mvc.repository;

import org.example.frontcontroller.mvc.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<String, User> users = new HashMap<>();

    public static void save (User user){
        users.put(user.getUserId(), user);
    }
}
