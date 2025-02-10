package com.example.learning_portal.learningportal.repository;
import com.example.learning_portal.learningportal.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
   @Query(value="SELECT c FROM course c WHERE course_category=:category",nativeQuery=true)
   List<Course> findByCategory(@Param("category") String category);
}
