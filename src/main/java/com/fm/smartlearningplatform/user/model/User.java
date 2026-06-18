package com.fm.smartlearningplatform.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private boolean enabled = true;

    @Column(name = "failed_login_attempt")
    @Builder.Default
    private int failedLoginAttempt = 0;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

//    // ─── User Profile ────────────────────────────────────────────────
//
//    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//   private UserProfile userProfile;
//
//    // ─── User Verification ────────────────────────────────────────────────
//
//    @OneToOne(mappedBy = "user",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
//            fetch = FetchType.LAZY
//    )
//    private UserVerification userVerification;
//
//    // ─── User Preference ────────────────────────────────────────────────
//
//    @OneToOne(mappedBy = "user",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
//            fetch = FetchType.LAZY
//    )
//    private UserPreference userPreference;

    // ─── User Social Link ────────────────────────────────────────────────

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<UserSocialLink> userSocialLinks = new ArrayList<>();

    // ─── User Skill ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<UserSkill> userSkills = new ArrayList<>();


    // ─── User Interest ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<UserInterest> userInterests = new ArrayList<>();


    // ─── User Role ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<UserRole> userRoles = new ArrayList<>();
}

