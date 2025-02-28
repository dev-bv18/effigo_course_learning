package com.example.app2.app2.controller;


import com.example.app2.app2.entity.User;
import com.example.app2.app2.service.UserService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userL2")
public class UserController {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/batch")
    public ResponseEntity<String> addUsers(@RequestBody List<User> users){
        return ResponseEntity.ok(userService.addUsers(users));
    }

    @PostMapping("/importUsers")
    public String jobLauncher(){

        final JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        try{

            final JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            return jobExecution.getStatus().toString();
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e){

         e.printStackTrace();

            return "Job failed with exception: " + e.getMessage();
        }
    }

}