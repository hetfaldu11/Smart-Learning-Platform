package com.fm.smartlearningplatform.model.course;

import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "courseLevel", nullable = false)
    private CourseLevel courseLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "courseStatus", nullable = false)
    @Builder.Default
    private CourseStatus courseStatus = CourseStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private CourseDetail courseDetail;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CourseMedia courseMedia;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CourseSupport courseSupport;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CoursePricing coursePricing;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CourseMessage> courseMessages = new HashSet<>();

    public void addMessage(MessageType messageType, String message){
        CourseMessage courseMessage = CourseMessage.builder()
                .messageType(messageType)
                .message(message)
                .build();

        this.courseMessages.add(courseMessage);
    }

    public void addMessage(CourseMessage courseMessage){
        this.courseMessages.add(courseMessage);
    }

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CourseRequirement> courseRequirements = new HashSet<>();

    public void addRequirement(String requirement){
        CourseRequirement courseRequirement = CourseRequirement.builder()
                .requirement(requirement)
                .build();

        this.courseRequirements.add(courseRequirement);
    }

    public void addRequirement(CourseRequirement courseRequirement){
        this.courseRequirements.add(courseRequirement);
    }

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CourseLearningOutcome> courseLearningOutcomes= new HashSet<>();

    public void addLearningOutcome(String outcome){
        CourseLearningOutcome courseLearningOutcome = CourseLearningOutcome.builder()
                .outcome(outcome)
                .build();

        this.courseLearningOutcomes.add(courseLearningOutcome);
    }

    public void addLearningOutcome(CourseLearningOutcome courseLearningOutcome){
        this.courseLearningOutcomes.add(courseLearningOutcome);
    }

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CourseLanguage> courseLanguages = new HashSet<>();

    public void addLanguage(Language language){
        CourseLanguage courseLanguage = CourseLanguage.builder()
                .language(language)
                .build();
        this.courseLanguages.add(courseLanguage);
    }

    public void addLanguage(CourseLanguage courseLanguage){
        this.courseLanguages.add(courseLanguage);
    }

//    @OneToMany(
//            mappedBy = "course",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<CourseAssistantInstructor> courseAssistantInstructors= new HashSet<>();

//    public void addAssistantInstructor(User user, InstructorRole instructorRole){
//        CourseAssistantInstructor courseAssistantInstructor = CourseAssistantInstructor.builder()
//                .role(instructorRole)
//                .build();
//
//        this.courseAssistantInstructors.add(courseAssistantInstructor);
//    }
//
//    public void addAssistantInstructor(CourseAssistantInstructor courseAssistantInstructor){
//        this.courseAssistantInstructors.add(courseAssistantInstructor);
//    }
}