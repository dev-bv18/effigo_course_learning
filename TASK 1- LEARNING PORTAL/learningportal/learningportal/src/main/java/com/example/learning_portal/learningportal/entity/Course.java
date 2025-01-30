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
    public enum Category{
        AWS,JAVA,REACT
    }
    @Id
    @Column(name="course_id",nullable = false,unique = true)
    private long id;

    @Column(name="course_category",nullable = false)
    private Category category;

    @Column(name="description",nullable = false)
    private String desc;

    @Column(name="price",nullable = false)
    private double cost;

    @Column(name="title",nullable = false,unique = true)
    private String course_title;
}
