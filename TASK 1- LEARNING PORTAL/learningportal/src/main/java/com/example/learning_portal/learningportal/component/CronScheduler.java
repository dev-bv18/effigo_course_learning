package com.example.learning_portal.learningportal.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class CronScheduler {
    private static final Logger logger = LoggerFactory.getLogger(CronScheduler.class);
    private boolean isBannerVisible = false;

    @Scheduled(cron = "*/5 * * * * *")
    public void showBanner() {
        isBannerVisible = true;
        logger.info("Banner is now Visible at: {}", LocalTime.now());
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void hideBanner() {
        isBannerVisible = false;
        logger.info("Banner is now Hidden at: {}", LocalTime.now());
    }

    public boolean isBannerVisible() {
        return isBannerVisible;
    }
}
