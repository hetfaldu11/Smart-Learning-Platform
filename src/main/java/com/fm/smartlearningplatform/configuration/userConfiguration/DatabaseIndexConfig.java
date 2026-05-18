package com.fm.smartlearningplatform.configuration.userConfiguration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseIndexConfig {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createPartialIndexes() {
        try {
            jdbcTemplate.execute("""
                CREATE UNIQUE INDEX IF NOT EXISTS uk_users_active_email
                ON users(email)
                WHERE deleted_at IS NULL
            """);
            log.info("Partial index created successfully.");
        } catch (Exception e) {
            log.warn("Could not create partial index: {}", e.getMessage());
        }
    }
}