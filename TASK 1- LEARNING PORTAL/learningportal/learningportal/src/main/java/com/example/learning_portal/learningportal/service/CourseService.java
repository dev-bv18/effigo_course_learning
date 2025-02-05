package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.dto.CourseDTO;
import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.mapper.CoursePopulator;
import com.example.learning_portal.learningportal.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static Logger log = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursePopulator coursePopulator;

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
    public Course updateCourse(CourseDTO courseDTO) {
        Course course=coursePopulator.courseRequestToDto(courseDTO);
        Optional<Course> oldCourse = courseRepository.findById(course.getId());
        if (oldCourse.isPresent()) {
            Course updatedCourse = oldCourse.get();
            updatedCourse.setCourseTitle(course.getCourseTitle());
            updatedCourse.setDesc(course.getDesc());
            updatedCourse.setCost(course.getCost());
            updatedCourse.setCategory(course.getCategory());
            return courseRepository.save(updatedCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + courseDTO.getId());
        }
    }

    public Course createNewCourse(CourseDTO courseDTO){
        log.info("COURSE SERVICE");
        log.info(courseDTO.getCourseTitle());
        log.info(courseDTO.getDesc());
        log.info(String.valueOf(courseDTO.getCost()));
        log.info(String.valueOf(courseDTO.getCategory()));
        log.info("Creating new course: {}", courseDTO);
        Course course = coursePopulator.courseRequestToDto(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return savedCourse;
    }
}
