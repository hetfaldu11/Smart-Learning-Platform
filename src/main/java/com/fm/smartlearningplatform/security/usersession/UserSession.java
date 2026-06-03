package com.fm.smartlearningplatform.security.usersession;


import com.fm.smartlearningplatform.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_sessions",
        indexes = {
                @Index(name = "idx_user_session_user", columnList = "user_id"),
                @Index(name = "idx_user_session_device", columnList = "device_identifier"),
                @Index(name = "idx_user_session_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token_hash",nullable = false)
    private String refreshTokenHash;

    @Column(name = "browser_name")
    private String browserName;

    @Column(name = "browser_version")
    private String browserVersion;

    @Column(name = "os_name")
    private String osName;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "device_identifier", nullable = false, length = 100)
    private String deviceIdentifier;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SessionStatus status = SessionStatus.ACTIVE;

    @Column(name = "trusted")
    @Builder.Default
    private boolean trusted = false;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_active_at")
    private LocalDateTime lastActiveAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @Column(name = "expires_at",nullable = false)
    private LocalDateTime expiresAt;
}