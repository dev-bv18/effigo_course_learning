package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.dto.UsersDTO;
import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.mapper.UsersPopulator;
import com.example.learning_portal.learningportal.repository.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersPopulator usersPopulator;

    private static Logger log= LoggerFactory.getLogger(UsersService.class);
    public List<UsersDTO> getAllUsers(){

        return usersRepository.findAll().stream().map(usersPopulator::usersToDto).collect(Collectors.toList());
    }
    public UsersDTO getOneUser(Long id){
        Optional<Users> oneUser=usersRepository.findById(id);
        return usersPopulator.optionalUserToDTO(oneUser);
    }
    public void deleteUser(Long id){
       usersRepository.deleteById(id);
    }
    public UsersDTO updateUsers(UsersDTO usersDTO) {
        Users users=usersPopulator.dtoToUsers(usersDTO);
        Optional<Users> oldUser = usersRepository.findById(users.getId());

        if (oldUser.isPresent()) {
            Users updatedUser = oldUser.get();
            if (usersDTO.getPassWord() != null && !usersDTO.getPassWord().isEmpty()) {
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

            updatedUser.setUserName(users.getUserName());
            updatedUser.setUserRole(users.getUserRole());
            updatedUser.setRegistrationDateTime(oldUser.get().getRegistrationDateTime());

            return usersPopulator.usersToDto(usersRepository.save(updatedUser));
        } else {
            throw new RuntimeException("User not found with id: " + usersDTO.getId());
        }
    }

    public UsersDTO createNewUsers(UsersDTO usersDTO){

        String password=String.valueOf(usersDTO.getPassWord());
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        usersDTO.setPassWord(hashed);
        log.info("Role: {}", usersDTO.getUserRole());
        log.info("Username: {}", usersDTO.getUserName());
        log.info("Password: {}", usersDTO.getPassWord());
        log.info("Registration Date: {}", usersDTO.getRegistrationDateTime());
        Users users=usersPopulator.dtoToUsers(usersDTO);
        Users savedUser=usersRepository.save(users);
        return usersPopulator.usersToDto(savedUser);
    }
}
