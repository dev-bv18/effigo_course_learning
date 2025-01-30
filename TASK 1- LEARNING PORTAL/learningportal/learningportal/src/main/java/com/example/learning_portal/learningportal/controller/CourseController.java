package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("course-details")
public class CourseController {
    @Autowired
    private CourseService courseService;

    private static Logger log= (Logger) LoggerFactory.getLogger(CourseController.class);

    @PostMapping("/create-course")
    public Course createNewCourse(@RequestBody Course course){
        log.info("COURSE CONTROLLER");
        log.info(course.getCourse_title());
        log.info(course.getDesc());
        log.info(String.valueOf(course.getCost()));
        log.info(String.valueOf(course.getCategory()));

        return courseService.createNewCourse(course);
    }
}
