package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("course-details")
public class CourseController {
    @Autowired
    private CourseService courseService;

    private static Logger log= (Logger) LoggerFactory.getLogger(CourseController.class);

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        String message="Course "+id+ " not found !";
        try{
        Optional<Course> course=courseService.getOneCourse(id);
        if(course.isPresent()){
            message="Course "+id+" deleted successfully";
            courseService.deleteCourse(id);
            log.info("Course deleted!");
        }
        }
        catch(Exception e){
            log.error("Error while fetching user:{}",e.getMessage(),e);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Course>> getOneCourse(@PathVariable Long id){
        try{
            log.info("fetching course with id "+id);
            return ResponseEntity.ok(courseService.getOneCourse(id));
        }
        catch(Exception e){
            log.error("Error while fetching course: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @GetMapping("/get-courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        try{
            log.info("Fetching courses....");
            return ResponseEntity.ok(courseService.getAllCourses());
        }
        catch(Exception e){
            log.error("Error while fetching courses: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @PostMapping("/create-course")
    public ResponseEntity<Course> createNewCourse(@RequestBody Course course){
       try{ log.info("COURSE CONTROLLER");
        log.info(course.getCourseTitle());
        log.info(course.getDesc());
        log.info(String.valueOf(course.getCost()));
        log.info(String.valueOf(course.getCategory()));

           Course savedCourse = courseService.createNewCourse(course);
           return ResponseEntity.ok(savedCourse);}
       catch(Exception e)
       {
           log.error("Error while creating course: {}", e.getMessage(), e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


       }
    }
}
