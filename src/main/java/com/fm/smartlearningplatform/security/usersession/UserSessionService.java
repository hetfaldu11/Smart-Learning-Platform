package com.fm.smartlearningplatform.security.usersession;


import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;

    public String createRefreshToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public boolean isRefreshTokenValid(String rawToken, UserSession session) {
        return passwordEncoder.matches(rawToken, session.getRefreshTokenHash());
    }

    public UserSession findValidSession(Long userId, String deviceId, String refreshToken) {
        UserSession session = userSessionRepository.findByUserIdAndDeviceIdentifier(userId, deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found."));

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Session revoked.");
        }

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            session.setStatus(SessionStatus.EXPIRED);
            userSessionRepository.save(session);
            throw new RuntimeException("Refresh token expired.");
        }

        if (!passwordEncoder.matches(refreshToken, session.getRefreshTokenHash())) {
            throw new RuntimeException("Invalid refresh token.");
        }

        return session;
    }

    public void enforceSessionLimit(Long id){
        List<UserSession> sessions = userSessionRepository.findByUserIdAndStatusOrderByLastActiveAtAsc(id, SessionStatus.ACTIVE);
        if (sessions.size() > 2) {
            UserSession oldestSession = sessions.get(0);
            oldestSession.setStatus(SessionStatus.REVOKED);
            oldestSession.setRevokedAt(LocalDateTime.now());
            userSessionRepository.save(oldestSession);
        }
    }

    public UserSession findByDeviceId(String deviceId){
        return userSessionRepository.findByDeviceIdentifier(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found."));
    }

    @Transactional
    public void revokeSession(UserSession session) {
        session.setStatus(SessionStatus.REVOKED);
        session.setRevokedAt(LocalDateTime.now());
        userSessionRepository.save(session);
    }

    @Transactional
    public void revokeAllSessions(Long userId) {
        List<UserSession> sessions = userSessionRepository.findAllByUserIdAndStatus(userId,SessionStatus.ACTIVE);

        sessions.forEach(session -> {
            session.setStatus(SessionStatus.REVOKED);
            session.setRevokedAt(LocalDateTime.now());
        });

        userSessionRepository.saveAll(sessions);
    }

    @Transactional(readOnly = true)
    public Optional<UserSession> existingSessions(Long userId, String deviceId){
        return userSessionRepository.findByUserIdAndDeviceIdentifierAndStatus(userId, deviceId,SessionStatus.ACTIVE);
    }

    @Transactional
    public UserSession save(UserSession userSession){
        return userSessionRepository.save(userSession);
    }
}