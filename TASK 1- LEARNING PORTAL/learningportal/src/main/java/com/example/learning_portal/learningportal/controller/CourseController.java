package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.dto.CourseDTO;
import com.example.learning_portal.learningportal.exception.CourseFetchException;
import com.example.learning_portal.learningportal.exception.CourseNotFoundException;
import com.example.learning_portal.learningportal.mapper.CoursePopulator;
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

    @Autowired
    private CoursePopulator coursePopulator;

    private static Logger log = LoggerFactory.getLogger(CourseController.class);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        try {
            CourseDTO courseDTO = courseService.getOneCourse(id);
            if (courseDTO != null) {
                courseService.deleteCourse(id);
                log.info("Course {} deleted successfully!", id);
                return ResponseEntity.ok("Course " + id + " deleted successfully!");
            }
            else
                throw new CourseNotFoundException(id);
        }
        catch (CourseNotFoundException e) {
            log.error("Error deleting course: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting course " + id);  }
       }

    @GetMapping("{id}")
    public ResponseEntity<Optional<CourseDTO>> getOneCourse(@PathVariable Long id){
        try{
            log.info("fetching course with id "+id);

            CourseDTO courseDTO= courseService.getOneCourse(id);
            return ResponseEntity.ok(Optional.ofNullable(courseDTO));
        }
        catch(Exception e){
            log.error("Error while fetching course: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/update-course")
        public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO){
        try{
            log.info("Updating course data....");
            CourseDTO tempCourseDTO=courseService.updateCourse(courseDTO);
            return ResponseEntity.ok(tempCourseDTO);
        }
        catch(Exception e){
            log.error("Error while fetching courses: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CourseDTO>> findByCategory(@PathVariable String category){
        try{
            log.info("Fetching "+category+" courses ....");
            List<CourseDTO> courses=courseService.findByCategory(category);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("Error while fetching courses: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        try {
            log.info("Fetching courses....");
            List<CourseDTO> courses = courseService.getAllCourses();

            if (courses.isEmpty()) {
                throw new CourseFetchException("No courses available.");
            }

            return ResponseEntity.ok(courses);
        } catch (CourseFetchException e) {
            log.error("Course fetch error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching courses: {}", e.getMessage(), e);
            throw new CourseFetchException("An error occurred while fetching courses.");
        }
    }

    @PostMapping("/create-course")
    public ResponseEntity<CourseDTO> createNewCourse(@RequestBody CourseDTO courseDTO) {
        try { log.info("COURSE CONTROLLER");
        log.info(courseDTO.getCourseTitle());
        log.info(courseDTO.getDesc());
        log.info(String.valueOf(courseDTO.getCost()));
        log.info(String.valueOf(courseDTO.getCategory()));
        CourseDTO tempCourseDTO=courseService.createNewCourse(courseDTO);
        return ResponseEntity.ok(tempCourseDTO);
        } catch (Exception e) {
            log.error("Error while creating course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
