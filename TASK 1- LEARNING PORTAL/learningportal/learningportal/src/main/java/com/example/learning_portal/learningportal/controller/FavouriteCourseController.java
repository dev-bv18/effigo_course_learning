package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.entity.FavouriteCourse;
import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import com.example.learning_portal.learningportal.service.FavouriteCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("favourite-course-details")
public class FavouriteCourseController {
    @Autowired
    private FavouriteCourseService favouriteCourseService;
    private static Logger log= LoggerFactory.getLogger(FavouriteCourseController.class);

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFavouriteCourse(@PathVariable long id){
       try{ log.info("deleting favourite course ...");
            if(favouriteCourseService.getOneFavouriteCourse(id).isPresent()){
            favouriteCourseService.deleteFavouriteCourse(id);
            return ResponseEntity.ok("deleted favourite course :"+id+" successfully!!");}

       }
       catch(Exception e){
           log.error("Error while fetching user:{}",e.getMessage(),e);
       }
        return ResponseEntity.ok("No favourite course with id " +id+" found :(");
    }
    @GetMapping("/get-favourite-courses")
    public ResponseEntity<List<FavouriteCourse>> getAllFavouriteCourse(){
        return ResponseEntity.ok(favouriteCourseService.getAllFavouriteCourse());
    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<FavouriteCourse>> getOneFavouriteCourse(@PathVariable Long id){
        return ResponseEntity.ok(favouriteCourseService.getOneFavouriteCourse(id));
    }
    @PostMapping("/favourite-course")
    public ResponseEntity<FavouriteCourse> addFavouriteCourse(@RequestBody FavouriteCourse favouriteCourse){
        try{
            log.info("FAVOURITE COURSE CONTROLLER");
            log.info(favouriteCourse.toString());
            FavouriteCourse savedCourse=favouriteCourseService.addFavouriteCourse(favouriteCourse);
           return ResponseEntity.ok(savedCourse);
        }
        catch(Exception e)
        {
            log.error("Error while creating user:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

}
