package com.example.kafka1.kafkaProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProjApplication.class, args);
	}

}
