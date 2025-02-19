package com.example.learning_portal.learningportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LearningportalApplication {

	public static void main(String[] args) {

		SpringApplication.run(LearningportalApplication.class, args);
	}

}
