package com.example.learning_portal.learningportal.repository;

import com.example.learning_portal.learningportal.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
//    @Query("SELECT c from Course c where c.category=:category")
//    List<Course> findByCategory(Course.Category category);

}
