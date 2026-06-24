package com.fm.smartlearningplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@EnableAsync
@EnableScheduling
public class SmartLearningPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartLearningPlatformApplication.class, args);
    }
}