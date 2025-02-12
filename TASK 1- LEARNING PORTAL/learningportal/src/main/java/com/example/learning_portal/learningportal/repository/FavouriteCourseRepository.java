package com.example.learning_portal.learningportal.repository;

import com.example.learning_portal.learningportal.dto.projectionDto;
import com.example.learning_portal.learningportal.entity.FavouriteCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteCourseRepository extends JpaRepository<FavouriteCourse,Long> {


}
