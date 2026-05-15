package com.fm.smartlearningplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "users",
        indexes = {
            @Index(name = "idx_user_email", columnList = "email")
        })
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", nullable = false)
    @Email
    private String email ;

    @Column(name ="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name= "password", nullable = false)
    private String password;

    @Column(name= "enabled", nullable = false)
    private int enabled;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name= "failed_login_attempt")
    private int failedLoginAttempt;

    @Column(name= "last_seen_at")
    private LocalDateTime lastSeenAt;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "upLocalDateTimed_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", lastLoginAt=" + lastLoginAt +
                ", failedLoginAttempt=" + failedLoginAttempt +
                ", lastSeenAt=" + lastSeenAt +
                ", passwordChangedAt=" + passwordChangedAt +
                ", accountLockedUntil=" + accountLockedUntil +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }



}