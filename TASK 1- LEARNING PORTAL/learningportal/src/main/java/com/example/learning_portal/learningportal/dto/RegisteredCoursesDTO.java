package com.example.learning_portal.learningportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredCoursesDTO {
    private Long registrationId;
    private UsersDTO user;
    private CourseDTO course;
}
