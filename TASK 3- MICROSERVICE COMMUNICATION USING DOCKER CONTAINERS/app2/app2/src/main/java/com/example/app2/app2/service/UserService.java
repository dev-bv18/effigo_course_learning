package com.example.app2.app2.service;


import com.example.app2.app2.entity.User;

import com.example.app2.app2.repository.UsersRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }

    public String addUsers(List<User> users) {
        userRepository.saveAll(users);
        return "Users added successfully!";
    }
}
