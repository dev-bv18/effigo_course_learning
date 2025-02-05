package com.example.learning_portal.learningportal.dto;

import com.example.learning_portal.learningportal.entity.Course;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private Course.Category category;
    private String desc;
    private Double cost;
    private String courseTitle;

}
