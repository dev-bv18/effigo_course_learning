package com.example.learning_portal.learningportal.mapper;

import com.example.learning_portal.learningportal.dto.CourseDTO;
import com.example.learning_portal.learningportal.dto.RegisteredCoursesDTO;
import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisteredCoursesPopulator {
    @Mapping(target ="registrationId", source = "registrationId")
    @Mapping(target = "user", source="user")
    @Mapping(target = "course" ,source="course")
    RegisteredCourses dtoToRegisteredCourse(RegisteredCoursesDTO registeredCoursesDTO);
    RegisteredCoursesDTO registeredCoursetoDTO(RegisteredCourses registeredCourses);

}
