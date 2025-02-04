package com.example.learning_portal.learningportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="favourite_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteCourse {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="favourite_id")
    private Long favouriteId;
@ManyToOne
    @JoinColumn(name="registration_id")
    private RegisteredCourses registeredCourse;

}
