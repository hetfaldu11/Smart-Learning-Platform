package com.fm.smartlearningplatform.model.user;

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
@Builder
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
    @Builder.Default
    private int enabled = 1;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name= "failed_login_attempt")
    @Builder.Default
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
            orphanRemoval = true
    )
    private UserProfile userProfile;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<UserSocialLink> userSocialLinks = new HashSet<>();

    public void addLink(Platform platform,String url){
        UserSocialLink userSocialLink = new UserSocialLink();

        userSocialLink.setUser(this);
        userSocialLink.setPlatform(platform);
        userSocialLink.setUrl(url);

        this.userSocialLinks.add(userSocialLink);
    }

    public void addLink(UserSocialLink userSocialLink){
        this.userSocialLinks.add(userSocialLink);
    }

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private UserVerification userVerification;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private UserPreference userPreference;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserSkill> userSkills = new HashSet<>();

    public void addSkill(Skill skill){
        UserSkill userSkill = UserSkill.builder()
                                .user(this)
                                .skill(skill)
                                .build();
        this.userSkills.add(userSkill);
    }

    public void removeSkill(Skill skill) {
        this.userSkills.removeIf(us -> us.getSkill().equals(skill));
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserInterest> userInterests = new HashSet<>();

    public void addInterest(Interest interest){
        UserInterest userInterest = UserInterest.builder()
                                .user(this)
                                .interest(interest)
                                .build();
        this.userInterests.add(userInterest);
    }

    public void removeInterest(Interest interest) {
        this.userInterests.removeIf(us -> us.getInterest().equals(interest));
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> userRoles = new HashSet<>();

    public void addRole(Role role){
        UserRole userRole = UserRole.builder()
                                .user(this)
                                .role(role)
                                .build();
        this.userRoles.add(userRole);
    }

    public void removeRole(Role role) {
        this.userRoles.removeIf(us -> us.getRole().equals(role));
    }
}