package com.example.learning_portal.learningportal.repository;

import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredCoursesRepository extends JpaRepository<RegisteredCourses,Long> {
//    @Query(value = "SELECT * FROM RegisteredCourses WHERE user_id = :user_id",nativeQuery = true)
//    List<RegisteredCourses> findByUserId(Long userId);
}
