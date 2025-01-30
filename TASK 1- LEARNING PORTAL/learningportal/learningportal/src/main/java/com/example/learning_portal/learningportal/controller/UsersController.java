package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users-details")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/create-users")
    public Users createNewUsers(@RequestBody Users users){
        return usersService.createNewUsers(users);
    }
}
