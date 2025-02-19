package com.example.learning_portal.learningportal.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course with ID " + id + " not found!");
    }
}