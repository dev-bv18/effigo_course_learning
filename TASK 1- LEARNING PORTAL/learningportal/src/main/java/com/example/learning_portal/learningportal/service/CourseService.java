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
import java.util.stream.Collectors;

@Service
public class CourseService {

    private static Logger log = LoggerFactory.getLogger(CourseService.class);


    private CourseRepository courseRepository;
    private CoursePopulator coursePopulator;

    public CourseService(CourseRepository courseRepository,CoursePopulator coursePopulator){
        this.courseRepository=courseRepository;
        this.coursePopulator=coursePopulator;
    }


    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(coursePopulator::courseToDTO)
                .collect(Collectors.toList());
    }
    public List<CourseDTO> findByCategory(String category){
        List<Course> courses=courseRepository.findByCategory(category);

        return courses.stream().map(coursePopulator::courseToDTO).collect(Collectors.toList());
    }
    public CourseDTO getOneCourse(Long id){
        Optional<Course> oneCourse= courseRepository.findById(id);
        return coursePopulator.optionalCourseToDTO(oneCourse);
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }

    public CourseDTO updateCourse(CourseDTO courseDTO) {

        Course course=coursePopulator.courseRequestToDto(courseDTO);

        Optional<Course> oldCourse = courseRepository.findById(course.getId());

        if (oldCourse.isPresent()) {
            Course updatedCourse = oldCourse.get();
            updatedCourse.setCourseTitle(course.getCourseTitle());
            updatedCourse.setDesc(course.getDesc());
            updatedCourse.setCost(course.getCost());
            updatedCourse.setCategory(course.getCategory());
             return coursePopulator.courseToDTO(courseRepository.save(updatedCourse));
        } else {
            throw new RuntimeException("Course not found with id: " + courseDTO.getId());
        }
    }

    public CourseDTO createNewCourse(CourseDTO courseDTO){
        log.info("COURSE SERVICE");

        log.info("course title: {}, desc: {}", courseDTO.getCourseTitle(),courseDTO.getDesc());
        log.info(courseDTO.getCourseTitle());
        log.info(courseDTO.getDesc());
        log.info(String.valueOf(courseDTO.getCost()));
        log.info(String.valueOf(courseDTO.getCategory()));

        log.info("Creating new course: {}", courseDTO);

        Course course = coursePopulator.courseRequestToDto(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return coursePopulator.courseToDTO(savedCourse);
    }
}
