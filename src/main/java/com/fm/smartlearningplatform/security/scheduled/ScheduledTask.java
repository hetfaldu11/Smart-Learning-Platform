package com.fm.smartlearningplatform.security.scheduled;

import com.fm.smartlearningplatform.security.usersession.SessionStatus;
import com.fm.smartlearningplatform.security.usersession.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public final class ScheduledTask {

    private final UserSessionRepository userSessionRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupSessions() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        userSessionRepository.deleteByStatusAndRevokedAtBefore(SessionStatus.REVOKED, cutoff);
        userSessionRepository.deleteByStatusAndExpiresAtBefore(SessionStatus.EXPIRED, cutoff);
    }
}