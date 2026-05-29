package com.fm.smartlearningplatform.model.user;

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
    @Column(name = "email", nullable = false)
    private String email ;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name= "password_hash", nullable = false)
    private String passwordHash;

    @Column(name= "enabled", nullable = false)
    @Builder.Default
    private boolean enabled = true;

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

    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private UserProfile userProfile;

    // ─── User Social Link ────────────────────────────────────────────────

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<UserSocialLink> userSocialLinks = new ArrayList<>();

    public UserSocialLink addLink(Platform platform,String url){

        for(UserSocialLink userSocialLink : userSocialLinks){
            if(userSocialLink.getPlatform().equals(platform))
                throw new RuntimeException("Link is already existed.");
        }

        UserSocialLink userSocialLink = new UserSocialLink();

        userSocialLink.setUser(this);
        userSocialLink.setPlatform(platform);
        userSocialLink.setUrl(url);

        this.userSocialLinks.add(userSocialLink);

        return userSocialLink;
    }

    public UserSocialLink addLink(UserSocialLink userSocialLink){
        if(userSocialLinks.contains(userSocialLink))
            throw new RuntimeException("UserSocialLink is already attached.");

        this.userSocialLinks.add(userSocialLink);

        return userSocialLink;
    }

    // ─── User Verification ────────────────────────────────────────────────

    @OneToOne(mappedBy = "user",
             cascade = {CascadeType.PERSIST,CascadeType.MERGE}
            )
    private UserVerification userVerification;

    // ─── User Preference ────────────────────────────────────────────────

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private UserPreference userPreference;

    // ─── User Skill ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private List<UserSkill> userSkills = new ArrayList<>();

    public UserSkill addSkill(Skill skill){
        UserSkill userSkill = UserSkill.builder()
                                .user(this)
                                .skill(skill)
                                .build();

        this.userSkills.add(userSkill);

        return userSkill;
    }

    // ─── User Interest ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private List<UserInterest> userInterests = new ArrayList<>();

    public UserInterest addInterest(Interest interest){
        UserInterest userInterest = UserInterest.builder()
                .user(this)
                .interest(interest)
                .build();

        this.userInterests.add(userInterest);

        return userInterest;
    }

    // ─── User Role ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private List<UserRole> userRoles = new ArrayList<>();

    public UserRole addRole(Role role){
        UserRole userRole = UserRole.builder()
                .user(this)
                .role(role)
                .build();

        this.userRoles.add(userRole);

        return userRole;
    }
}

