package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.repository.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    private static Logger log= LoggerFactory.getLogger(UsersService.class);
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }
    public Optional<Users> getOneUser(Long id){
        return usersRepository.findById(id);
    }
    public void deleteUser(Long id){
       usersRepository.deleteById(id);
    }


    public Users createNewUsers(Users user){
        String password=String.valueOf(user.getPassWord());
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        user.setPassWord(hashed);
        log.info("Role: {}", user.getUserRole());
        log.info("Username: {}", user.getUserName());
        log.info("Password: {}", user.getPassWord());
        log.info("Registration Date: {}", user.getRegistrationDateTime());
        return usersRepository.save(user);
    }
}
