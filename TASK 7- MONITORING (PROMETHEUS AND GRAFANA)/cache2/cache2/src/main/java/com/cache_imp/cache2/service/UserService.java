package com.cache_imp.cache2.service;

import com.cache_imp.cache2.entity.User;
import com.cache_imp.cache2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUserToDatabase(User user) {
        return userRepository.save(user);
    }


    public boolean checkIfUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    public boolean deleteUserFromDatabase(String username) {
        List<User> user = userRepository.findAllByUsername(username).orElse(null);
        if (user != null) {
            userRepository.deleteAll(user);
            return true;
        }
        return false;
    }


    public User updateUserInDatabase(String username, User user) {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User getUserFromDatabase(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
