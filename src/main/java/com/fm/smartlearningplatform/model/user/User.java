package com.fm.smartlearningplatform.model.user;

import com.fm.smartlearningplatform.model.course.Course;
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
    private Set<UserAuthorization> authorizations = new HashSet<>();

    public void addRole(UserRole role) {

        UserAuthorization authorization =
                new UserAuthorization();

        authorization.setUser(this);
        authorization.setUserRole(role);

        this.authorizations.add(authorization);
    }

    public void addRole(UserAuthorization userAuthorization){
        this.authorizations.add(userAuthorization);
    }

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    public void addSkill(Skill skill){
        skills.add(skill);
        skill.getUsers().add(this);
    }

    public void removeSkill(Skill skill){
        skills.remove(skill);
        skill.getUsers().remove(this);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @Builder.Default
    private Set<Interest> interests = new HashSet<>();

    public void addInterest(Interest interest){
        interests.add(interest);
        interest.getUsers().add(this);
    }

    public void removeInterest(Interest interest){
        interests.remove(interest);
        interest.getUsers().remove(this);
    }
}