package com.ten.service.activities;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ten.service.model.User;

@RestController
public class UserActivity {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") String id) {
        return new User(Long.valueOf(id), "User" + id, "120.4", "101.2");
    }
}
