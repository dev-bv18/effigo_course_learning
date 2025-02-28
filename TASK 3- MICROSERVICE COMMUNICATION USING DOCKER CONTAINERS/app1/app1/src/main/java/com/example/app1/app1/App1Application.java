package com.example.app1.app1;

import com.example.app1.app1.entity.User;
import com.example.app1.app1.service.UserFileReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class App1Application implements CommandLineRunner {

	private static final String BASE_URL = "http://app2:8088/userL2/batch";

	private final RestTemplate restTemplate = new RestTemplate();
	private final UserFileReaderService userFileReaderService;

	public App1Application(UserFileReaderService userFileReaderService) {
		this.userFileReaderService = userFileReaderService;
	}

	public static void main(String[] args) {
		SpringApplication.run(App1Application.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			System.out.println("Reading users from file in App1...");
			List<User> users = userFileReaderService.readUsersFromFile();

			System.out.println("Sending users to App2...");
			restTemplate.postForObject(BASE_URL, users, String.class);

			System.out.println("Users sent to App2 successfully!");
		} catch (Exception e) {
			System.err.println("Error during startup data transfer: " + e.getMessage());
		}
	}
}
