package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.dto.UsersDTO;
import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.mapper.UsersPopulator;
import com.example.learning_portal.learningportal.repository.UsersRepository;
import org.mapstruct.control.MappingControl;
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
    @Autowired
    private UsersPopulator usersPopulator;

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
    public Users updateUsers(UsersDTO usersDTO) {
        Optional<Users> oldUser = usersRepository.findById(usersDTO.getId());

        if (oldUser.isPresent()) {
            Users updatedUser = oldUser.get();

            // Only update password if a new one is provided
            if (usersDTO.getPassWord() != null && !usersDTO.getPassWord().isEmpty()) {
                // Optionally, check if the provided password is different from the existing one
                if (!BCrypt.checkpw(usersDTO.getPassWord(), oldUser.get().getPassWord())) {
                    log.info("Updating password for user ID: {}", usersDTO.getId());
                    String hashedPassword = BCrypt.hashpw(usersDTO.getPassWord(), BCrypt.gensalt(12));
                    updatedUser.setPassWord(hashedPassword);
                } else {
                    log.info("Password is the same as the old password. Not updating for user ID: {}", usersDTO.getId());
                }
            } else {
                log.info("No new password provided, keeping the old password for user ID: {}", usersDTO.getId());
            }

            // Update other fields (username, role, etc.)
            updatedUser.setUserName(usersDTO.getUserName());
            updatedUser.setUserRole(usersDTO.getUserRole());
            updatedUser.setRegistrationDateTime(oldUser.get().getRegistrationDateTime()); // Retain original registration date

            return usersRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + usersDTO.getId());
        }
    }

    public Users createNewUsers(UsersDTO usersDTO){

        String password=String.valueOf(usersDTO.getPassWord());
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        usersDTO.setPassWord(hashed);
        log.info("Role: {}", usersDTO.getUserRole());
        log.info("Username: {}", usersDTO.getUserName());
        log.info("Password: {}", usersDTO.getPassWord());
        log.info("Registration Date: {}", usersDTO.getRegistrationDateTime());
        Users users=usersPopulator.dtoToUsers(usersDTO);
        Users savedUser=usersRepository.save(users);
        return savedUser;
    }
}
