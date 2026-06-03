package com.fm.smartlearningplatform.scheduled;

import com.fm.smartlearningplatform.security.usersession.SessionStatus;
import com.fm.smartlearningplatform.security.usersession.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ScheduledTask {

    public final UserSessionRepository userSessionRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupSessions() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        userSessionRepository.deleteByStatusAndRevokedAtBefore(SessionStatus.REVOKED, cutoff);
        userSessionRepository.deleteByStatusAndExpiresAtBefore(SessionStatus.EXPIRED, cutoff);
    }
}
