package com.example.springbatch.springBatch.config;

import com.example.springbatch.springBatch.entity.Student;
import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor implements ItemProcessor<Student, Student> {
    public Student process(Student student) throws Exception{
        return student;
    }
}

