package com.fm.smartlearningplatform.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Builder
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "about_me",columnDefinition = "TEXT")
    private String aboutMe;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EducationLevel educationLevel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "home_street")),
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "state", column = @Column(name = "home_state")),
            @AttributeOverride(name = "country", column = @Column(name = "home_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "home_postal_code")),
            @AttributeOverride(name = "fullAddress", column = @Column(name = "home_full_address"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "state", column = @Column(name = "work_state")),
            @AttributeOverride(name = "country", column = @Column(name = "work_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "work_postal_code")),
            @AttributeOverride(name = "fullAddress", column = @Column(name = "work_full_address"))
    })
    private Address workAddress;

    @Column(name = "institute_name")
    private String instituteName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}