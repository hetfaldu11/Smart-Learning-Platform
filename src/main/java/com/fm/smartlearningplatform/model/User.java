package com.fm.smartlearningplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        indexes = {
            @Index(name = "idx_user_email", columnList = "email")
        })
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userProfile")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email ;

    @Column(name= "password_hash", nullable = false)
    private String passwordHash;

    @Column(name= "enabled", nullable = false)
    private int enabled = 1;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name= "failed_login_attempt")
    private int failedLoginAttempt = 0;

    @Column(name= "last_seen_at")
    private LocalDateTime lastSeenAt;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private UserProfile userProfile;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserAuthorization> authorizations = new HashSet<>();

    public void addRole(UserRole role) {

        UserAuthorization authorization =
                new UserAuthorization();

        authorization.setUser(this);
        authorization.setUserRole(role);

        this.authorizations.add(authorization);
    }
}