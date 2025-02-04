package com.example.learning_portal.learningportal.dto;

import com.example.learning_portal.learningportal.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Course.Category category;
    private String desc;
    private Double cost;
    private String courseTitle;

}
