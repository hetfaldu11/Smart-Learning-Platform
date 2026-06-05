package com.fm.smartlearningplatform.security.usersession;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByDeviceIdentifier(String deviceId);
    boolean existsByUserIdAndDeviceIdentifier(Long id, String deviceIdentifier);
    Optional<UserSession> findByUserIdAndDeviceIdentifierAndTrustedTrue(Long userId,String deviceID);
    Boolean existsByUserIdAndDeviceIdentifierAndTrustedTrue(Long userId,String deviceID);
    Optional<UserSession> findByUserIdAndDeviceIdentifier(Long userId, String deviceIdentifier);
    Optional<UserSession> findByUserIdAndDeviceIdentifierAndStatus(Long userId, String deviceIdentifier, SessionStatus status);
    List<UserSession> findByUserId(Long userId);
    List<UserSession> findAllByStatus(SessionStatus status);
    Optional<UserSession> findByIdAndStatus(Long id, SessionStatus status);
    List<UserSession> findAllByUserIdAndStatus(Long userId, SessionStatus status);
    List<UserSession> findByUserIdAndStatusOrderByCreatedAtAsc(Long userId, SessionStatus status);
    List<UserSession> findByUserIdAndStatusOrderByLastActiveAtAsc(Long userId, SessionStatus status);
    Long deleteByStatusAndRevokedAtBefore(SessionStatus status, LocalDateTime revokedAtBefore);
    Long deleteByStatusAndExpiresAtBefore(SessionStatus status, LocalDateTime expiresAtBefore);
}