package com.example.learning_portal.learningportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="course",schema = "public")
public class Course {

    public enum Category{
        AWS,JAVA,REACT,C
    }
    @Id
    @Column(name="course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="course_category",nullable = false)
    private Category category;

    @Column(name="description",nullable = false)
    private String desc;

    @Column(name="price",nullable = false)
    private Double cost;

    @Column(name="title",nullable = false,unique = true)
    private String courseTitle;
}
