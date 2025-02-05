package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.dto.RegisteredCoursesDTO;
import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import com.example.learning_portal.learningportal.service.RegisteredCoursesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("registered-courses-details")
public class RegisteredCoursesController {
    @Autowired
    private RegisteredCoursesService registeredCoursesService;
    private static Logger log= LoggerFactory.getLogger(RegisteredCoursesController.class);

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRegisteredCourse(@PathVariable Long id){
        log.info("deleting registered course... ");
        if(registeredCoursesService.getOneRegisteredCourse(id).isPresent()){
        registeredCoursesService.deleteRegisteredCourse(id);
        return ResponseEntity.ok("deleted registered course "+id+ " successfully!");}
        return ResponseEntity.ok("No registered course with id " +id+" found :(");
    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<RegisteredCourses>> getOneRegisteredCourse(@PathVariable Long id){
    return ResponseEntity.ok(registeredCoursesService.getOneRegisteredCourse(id));
    }
    @GetMapping("/get-registered-courses")
    public ResponseEntity<List<RegisteredCourses>> getAllRegisteredCourses(){
        return ResponseEntity.ok(registeredCoursesService.getAllRegisteredCourses());
    }
    @PostMapping("/register-course")
    public ResponseEntity<RegisteredCourses> createNewRegisteredCourses(@RequestBody RegisteredCoursesDTO registeredCoursesDTO){
        try{
            log.info("REGISTERED COURSE CONTROLLER");
            RegisteredCourses savedCourse=registeredCoursesService.createNewRegisteredCourses(registeredCoursesDTO);
            return ResponseEntity.ok(savedCourse);
        }
        catch(Exception e)
        {
            log.error("Error while creating user:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }


}
