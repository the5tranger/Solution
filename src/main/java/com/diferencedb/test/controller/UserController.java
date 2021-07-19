package com.diferencedb.test.controller;

import com.diferencedb.test.model.User;
import com.diferencedb.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/users",
        produces = "application/json",
        method = RequestMethod.POST)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public User insertUser(@RequestBody User user) {
        return userService.save(user);
    }
}