package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    private static Logger log= LoggerFactory.getLogger(UsersService.class);

    public Users createNewUsers(Users user){
        log.info("USERS SERVICE");
        log.info(String.valueOf(user.getUserRole()));
        log.info(String.valueOf(user.getUserName()));
        log.info(String.valueOf(user.getPassWord()));
        log.info(String.valueOf(user.getRegistrationDateTime()));
        return usersRepository.save(user);
    }
}
