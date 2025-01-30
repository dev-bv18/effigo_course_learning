package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Users createNewUsers(Users user){

        return usersRepository.save(user);
    }
}
