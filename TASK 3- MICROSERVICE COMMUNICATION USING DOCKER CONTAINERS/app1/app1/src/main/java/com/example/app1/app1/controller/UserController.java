package com.example.app1.app1.controller;

import com.example.app1.app1.entity.User;
import com.example.app1.app1.service.UserFileReaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RestTemplate restTemplate;
    private final UserFileReaderService userFileReaderService;

    private static final String BASE_URL = "http://app2:8088";

    public UserController(UserFileReaderService userFileReaderService) {
        this.restTemplate = new RestTemplate();
        this.userFileReaderService = userFileReaderService;
    }

    // Fetch a single user from App2
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        try {
            User user = restTemplate.getForObject(BASE_URL + "/userL2/" + id, User.class);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println("Error fetching user: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // Manually send a batch of users (this is optional, more useful for testing)
    @PostMapping("/batch")
    public ResponseEntity<String> sendBatchUsers(@RequestBody List<User> users) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/userL2/batch", users, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error sending batch users: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to send batch");
        }
    }

    // New: Read users.json in App1, and send to App2 in one call
    @PostMapping("/sendUsersFromFile")
    public ResponseEntity<String> sendUsersFromFile() {
        try {
            List<User> users = userFileReaderService.readUsersFromFile();
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/userL2/batch", users, String.class);
            return ResponseEntity.ok("Users read from file and sent to App2 successfully.");
        } catch (Exception e) {
            System.err.println("Error reading and sending users: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to read and send users");
        }
    }

    // Old: Trigger App2's internal batch import (you can remove this if not needed)
    @PostMapping("/importUsers")
    public ResponseEntity<String> triggerApp2BatchImport() {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/userL2/importUsers", null, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error triggering import: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to start import");
        }
    }
}
