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
    public ResponseEntity<UsersDTO> createNewUsers(@RequestBody UsersDTO usersDTO){
      try{  log.info("USERS CONTROLLER");
//        log.info(String.valueOf(users.getUserRole()));
//        log.info(String.valueOf(users.getUserName()));
//        log.info(String.valueOf(users.getPassWord()));
//        log.info(String.valueOf(users.getRegistrationDateTime()));

        log.info("creating user...");
        UsersDTO savedUsers=usersService.createNewUsers(usersDTO);
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
      try{
          UsersDTO usersDTO=usersService.getOneUser(id);

        if(usersDTO!=null){
            usersService.deleteUser(id);
            log.info("User {} deleted successfully!", id);
            return ResponseEntity.ok("User " + id + " deleted successfully!");
        }
      }
      catch(Exception e){
          log.error("Error while fetching user:{}",e.getMessage(),e);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + id + " not found!");

      }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + id + " not found!");

}
@PutMapping("/update-users")
public ResponseEntity<UsersDTO> updateUsers(@RequestBody UsersDTO usersDTO){
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
    public ResponseEntity<Optional<UsersDTO>> getOneUser(@PathVariable Long id)
    {
        try{
            log.info("fetching user with id "+id);
            return ResponseEntity.ok(Optional.ofNullable(usersService.getOneUser(id)));
        }
        catch (Exception e)
        {
            log.error("Error while fetching user:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

@GetMapping("/get-users")
    public ResponseEntity<List<UsersDTO>> getAllUsers()
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
