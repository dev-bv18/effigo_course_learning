package com.example.learning_portal.learningportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="course")
public class Course {
    @Id
    @Column(name="course_id")
    private long id;

    @Column(name="course_category")
    private long category;

    @Column(name="description")
    private String desc;

    @Column(name="price")
    private double cost;

    @Column(name="title")
    private String course_title;
}
