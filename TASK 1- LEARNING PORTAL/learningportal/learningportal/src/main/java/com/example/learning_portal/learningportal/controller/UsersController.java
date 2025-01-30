package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log= LoggerFactory.getLogger(UsersController.class);
    @PostMapping("/create-users")
    public Users createNewUsers(@RequestBody Users users){
        log.info("USERS CONTROLLER");
        log.info(String.valueOf(users.getUserRole()));
        log.info(String.valueOf(users.getUserName()));
        log.info(String.valueOf(users.getPassWord()));
        log.info(String.valueOf(users.getRegistrationDateTime()));

        return usersService.createNewUsers(users);
    }
}
