package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course createNewCourse(Course course){
        return courseRepository.save(course);
    }
}
