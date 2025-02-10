package com.example.learning_portal.learningportal.repository;

import com.example.learning_portal.learningportal.entity.FavouriteCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteCourseRepository extends JpaRepository<FavouriteCourse,Long> {
}
