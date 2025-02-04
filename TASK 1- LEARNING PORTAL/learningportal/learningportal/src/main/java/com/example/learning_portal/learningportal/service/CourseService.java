package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static Logger log= LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses()
    {
        return courseRepository.findAll();
    }
    public Optional<Course> getOneCourse(Long id){
        return courseRepository.findById(id);
    }
    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }
    public Course createNewCourse(Course course){
        log.info("COURSE SERVICE");
        log.info(course.getCourseTitle());
        log.info(course.getDesc());
        log.info(String.valueOf(course.getCost()));
        log.info(String.valueOf(course.getCategory()));
        return courseRepository.save(course);
    }
}
