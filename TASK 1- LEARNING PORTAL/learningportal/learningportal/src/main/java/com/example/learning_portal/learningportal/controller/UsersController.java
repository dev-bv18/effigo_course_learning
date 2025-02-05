package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.dto.UsersDTO;
import com.example.learning_portal.learningportal.entity.Users;
import com.example.learning_portal.learningportal.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users-details")
public class UsersController {
    @Autowired
    private UsersService usersService;

    private static Logger log= LoggerFactory.getLogger(UsersController.class);
    @PostMapping("/create-users")
    public ResponseEntity<Users> createNewUsers(@RequestBody UsersDTO usersDTO){
      try{  log.info("USERS CONTROLLER");
//        log.info(String.valueOf(users.getUserRole()));
//        log.info(String.valueOf(users.getUserName()));
//        log.info(String.valueOf(users.getPassWord()));
//        log.info(String.valueOf(users.getRegistrationDateTime()));

        log.info("creating user...");
        Users savedUsers=usersService.createNewUsers(usersDTO);
        log.info("successfully created user...");
        return ResponseEntity.ok(savedUsers);}
    catch(Exception e)
    {
        log.error("Error while creating user:{}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}

@DeleteMapping("{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id){
    String message="User with id "+id +" not found!";
      try{  Optional<Users> user=usersService.getOneUser(id);

        if(user.isPresent()){
             message="User "+id+" deleted successfully";
            usersService.deleteUser(id);
            log.info("user deleted!");
       }


      }
      catch(Exception e){
          log.error("Error while fetching user:{}",e.getMessage(),e);
      }
    return ResponseEntity.ok(message);
}
@PutMapping("/update-users")
public ResponseEntity<Users> updateUsers(@RequestBody UsersDTO usersDTO){
        try{
            log.info("Updating user data....");
            return ResponseEntity.ok(usersService.updateUsers(usersDTO));
        }
        catch(Exception e){
            log.error("Error while fetching courses: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
}

    @GetMapping("{id}")
    public ResponseEntity<Optional<Users>> getOneUser(@PathVariable Long id)
    {
        try{
            log.info("fetching user with id "+id);
            return ResponseEntity.ok(usersService.getOneUser(id));
        }
        catch (Exception e)
        {
            log.error("Error while fetching user:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

@GetMapping("/get-users")
    public ResponseEntity<List<Users>> getAllUsers()
{
    try{
        log.info("fetching users..");
        return ResponseEntity.ok(usersService.getAllUsers());

    }
    catch(Exception e)
    {
        log.error("Error while fetching users:{}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}
