package com.fm.smartlearningplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmartLearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLearningPlatformApplication.class, args);
    }

}