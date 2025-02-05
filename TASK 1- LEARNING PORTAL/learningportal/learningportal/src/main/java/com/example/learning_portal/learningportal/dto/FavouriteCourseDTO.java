package com.example.learning_portal.learningportal.dto;

import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteCourseDTO {
    private Long favouriteId;
    private RegisteredCoursesDTO registeredCourse;
}
