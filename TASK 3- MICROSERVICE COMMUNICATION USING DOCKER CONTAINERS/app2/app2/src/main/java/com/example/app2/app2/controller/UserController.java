package com.example.app2.app2.controller;

import com.example.app2.app2.entity.User;
import com.example.app2.app2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userL2")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Job job;
    private final JobLauncher jobLauncher;
    private final UserService userService;

    public UserController(Job job, JobLauncher jobLauncher, UserService userService) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        logger.info("Fetched user: {}", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/batch")
    public ResponseEntity<String> addUsers(@RequestBody List<User> users) {
        logger.info("Adding batch of {} users.", users.size());
        String response = userService.addUsers(users);
        logger.info("Batch added successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/importUsers")
    public String jobLauncher() {
        logger.info("Starting job importUsers...");

        final JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            logger.info("Job started successfully with status: {}", jobExecution.getStatus());
            return jobExecution.getStatus().toString();
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error("Job is already running!", e);
            return "Job failed: Already running!";
        } catch (JobRestartException e) {
            logger.error("Job restart failed!", e);
            return "Job failed: Restart not allowed!";
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.error("Job already completed!", e);
            return "Job failed: Already completed!";
        } catch (JobParametersInvalidException e) {
            logger.error("Invalid job parameters!", e);
            return "Job failed: Invalid parameters!";
        } catch (Exception e) {
            logger.error("Unexpected error during job execution!", e);
            return "Job failed due to unexpected error!";
        }
    }
}
