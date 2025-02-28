package com.example.app1.app1.controller;

import com.example.app1.app1.entity.User;
import com.example.app1.app1.service.UserFileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final RestTemplate restTemplate;
    private final UserFileReaderService userFileReaderService;

    private static final String BASE_URL = "http://app2:8088";

    public UserController(UserFileReaderService userFileReaderService) {
        this.restTemplate = new RestTemplate();
        this.userFileReaderService = userFileReaderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        try {
            User user = restTemplate.getForObject(BASE_URL + "/userL2/" + id, User.class);
            logger.info("Successfully fetched user: {}", user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error fetching user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<String> sendBatchUsers(@RequestBody List<User> users) {
        logger.info("Sending batch of {} users to App2.", users.size());
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/userL2/batch", users, String.class);
            logger.info("Batch sent successfully. Response: {}", response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            logger.error("Error sending batch users: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to send batch");
        }
    }

    @PostMapping("/sendUsersFromFile")
    public ResponseEntity<String> sendUsersFromFile() {
        logger.info("Reading users from file and sending to App2...");
        try {
            List<User> users = userFileReaderService.readUsersFromFile();
            logger.info("Read {} users from file.", users.size());
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/userL2/batch", users, String.class);
            logger.info("Users sent successfully to App2.");
            return ResponseEntity.ok("Users read from file and sent to App2 successfully.");
        } catch (Exception e) {
            logger.error("Error reading and sending users from file: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to read and send users");
        }
    }

    @PostMapping("/importUsers")
    public ResponseEntity<String> triggerApp2BatchImport() {
        logger.info("Triggering batch import in App2...");
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/userL2/importUsers", null, String.class);
            logger.info("Import triggered successfully. Response: {}", response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            logger.error("Error triggering import: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to start import");
        }
    }
}
