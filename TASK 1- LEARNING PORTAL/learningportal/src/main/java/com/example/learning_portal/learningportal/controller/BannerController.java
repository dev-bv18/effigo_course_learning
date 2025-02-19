package com.example.learning_portal.learningportal.controller;

import com.example.learning_portal.learningportal.component.CronScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banner")
public class BannerController {
    private final CronScheduler cronScheduler;
    public BannerController(CronScheduler cronScheduler){
        this.cronScheduler=cronScheduler;
    }
    @GetMapping("/status")
    public boolean isBannerVisible() {
        return cronScheduler.isBannerVisible();
    }
}
