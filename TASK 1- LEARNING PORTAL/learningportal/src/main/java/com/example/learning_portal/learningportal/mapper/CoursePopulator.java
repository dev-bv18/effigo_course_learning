package com.example.learning_portal.learningportal.mapper;

import com.example.learning_portal.learningportal.dto.CourseDTO;
import com.example.learning_portal.learningportal.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CoursePopulator {
//    CoursePopulator INSTANCE= Mappers.getMapper(CoursePopulator.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target="category", source = "category")
    @Mapping(target = "desc", source = "desc")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "courseTitle", source = "courseTitle")
    Course courseRequestToDto(CourseDTO courseDTO);

    CourseDTO courseToDTO(Course course);

    default CourseDTO optionalCourseToDTO(Optional<Course> course) {
        if (course.isPresent()) {
            return courseToDTO(course.get());
        } else {
            return null;
        }
    }

}
