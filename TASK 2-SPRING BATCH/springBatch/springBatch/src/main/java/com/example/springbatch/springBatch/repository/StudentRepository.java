package com.example.springbatch.springBatch.repository;

import com.example.springbatch.springBatch.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
