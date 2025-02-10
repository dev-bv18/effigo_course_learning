package com.example.learning_portal.learningportal.repository;

import com.example.learning_portal.learningportal.entity.Course;
import com.example.learning_portal.learningportal.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
  }
