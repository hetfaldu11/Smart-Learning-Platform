package com.fm.smartlearningplatform.security.scheduled;

import com.fm.smartlearningplatform.security.usersession.SessionStatus;
import com.fm.smartlearningplatform.security.usersession.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
public final class ScheduledTask {

    private final UserSessionRepository userSessionRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupSessions() {
        log.info("Session cleanup job started");
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        Long revoked = userSessionRepository.deleteByStatusAndRevokedAtBefore(SessionStatus.REVOKED, cutoff);
        Long expired = userSessionRepository.deleteByStatusAndExpiresAtBefore(SessionStatus.EXPIRED, cutoff);
        log.info("Session cleanup job completed, revoked deleted : {}, expired deleted : {}", revoked, expired);
    }
}