package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.dto.RegisteredCoursesDTO;
import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import com.example.learning_portal.learningportal.mapper.RegisteredCoursesPopulator;
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

    @Autowired
    private RegisteredCoursesPopulator registeredCoursesPopulator;

    private static Logger log= LoggerFactory.getLogger(RegisteredCoursesController.class);

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRegisteredCourse(@PathVariable Long id) {
        try {
            log.info("Deleting registered course with ID: {}", id);
            RegisteredCoursesDTO registeredCoursesDTO = registeredCoursesService.getOneRegisteredCourse(id);

            if (registeredCoursesDTO != null) {
                registeredCoursesService.deleteRegisteredCourse(id);
                log.info("Registered course {} deleted successfully!", id);
                return ResponseEntity.ok("Deleted registered course " + id + " successfully!");
            } else {
                log.warn("No registered course found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No registered course with ID " + id + " found :(");
            }
        } catch (Exception e) {
            log.error("Error deleting registered course: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No registered course with ID " + id + " found!");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<RegisteredCoursesDTO>> getOneRegisteredCourse(@PathVariable Long id){
    return ResponseEntity.ok(Optional.ofNullable(registeredCoursesService.getOneRegisteredCourse(id)));
    }
    @GetMapping("/get-registered-courses")
    public ResponseEntity<List<RegisteredCoursesDTO>> getAllRegisteredCourses(){
        return ResponseEntity.ok(registeredCoursesService.getAllRegisteredCourses());
    }
    @PostMapping("/register-course")
    public ResponseEntity<RegisteredCoursesDTO> createNewRegisteredCourses(@RequestBody RegisteredCoursesDTO registeredCoursesDTO){
        try{
            log.info("REGISTERED COURSE CONTROLLER");
            RegisteredCoursesDTO savedCourse=registeredCoursesService.createNewRegisteredCourses(registeredCoursesDTO);
            return ResponseEntity.ok(savedCourse);
        }
        catch(Exception e)
        {
            log.error("Error while creating user:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }


}
