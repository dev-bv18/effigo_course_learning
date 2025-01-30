package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.service.CourseService;
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

    @PostMapping("/create-course")
    public Course createNewCourse(@RequestBody Course course){
        return courseService.createNewCourse(course);
    }
}
