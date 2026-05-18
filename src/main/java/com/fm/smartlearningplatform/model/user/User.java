package com.fm.smartlearningplatform.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.processing.Find;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private UserProfile userProfile;

    // ─── User Social Link ────────────────────────────────────────────────

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<UserSocialLink> userSocialLinks = new HashSet<>();

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
    private Set<UserSkill> userSkills = new HashSet<>();

    public UserSkill addSkill(Skill skill){
        for(UserSkill userSkill : userSkills){
            if(userSkill.getSkill().equals(skill))
                throw new RuntimeException("Skill is already attached.");
        }

        UserSkill userSkill = UserSkill.builder()
                                .user(this)
                                .skill(skill)
                                .build();

        this.userSkills.add(userSkill);

        return userSkill;
    }

    public void removeSkill(Skill skill) {

        for(UserSkill userSkill : userSkills){
            if(userSkill.getSkill().equals(skill)){
                this.userSkills.removeIf(us -> us.getSkill().equals(skill));
                return;
            }
        }

        throw new RuntimeException("Skill is already detached.");
    }

    // ─── User Interest ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private Set<UserInterest> userInterests = new HashSet<>();

    public UserInterest addInterest(Interest interest){
        for(UserInterest userInterest : userInterests){
            if(userInterest.getInterest().equals(interest))
                throw new RuntimeException("Interest is already attached.");
        }

        UserInterest userInterest = UserInterest.builder()
                .user(this)
                .interest(interest)
                .build();

        this.userInterests.add(userInterest);

        return userInterest;
    }

    public void removeInterest(Interest interest) {

        for(UserInterest userInterest : userInterests){
            if(userInterest.getInterest().equals(interest)){
                this.userInterests.removeIf(us -> us.getInterest().equals(interest));
                return;
            }
        }

        throw new RuntimeException("Interest is already detached.");
    }

    // ─── User Role ────────────────────────────────────────────────

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private Set<UserRole> userRoles = new HashSet<>();

    public UserRole addRole(Role role){
        for(UserRole userRole : userRoles){
            if(userRole.getRole().equals(role))
                throw new RuntimeException("Role is already attached.");
        }

        UserRole userRole = UserRole.builder()
                .user(this)
                .role(role)
                .build();

        this.userRoles.add(userRole);

        return userRole;
    }

    public void removeRole(Role role) {

        for(UserRole userRole : userRoles){
            if(userRole.getRole().equals(role)){
                this.userRoles.removeIf(us -> us.getRole().equals(role));
                return;
            }
        }

        throw new RuntimeException("Role is already detached.");
    }
}