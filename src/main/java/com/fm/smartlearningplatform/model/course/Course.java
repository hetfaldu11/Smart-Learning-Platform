package com.fm.smartlearningplatform.model.course;

import com.fm.smartlearningplatform.model.lesson.Section;
import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "subtitle", nullable = false)
    private String subtitle;

    @ManyToOne
    @JoinColumn(name = "course_level_id", nullable = false)
    private CourseLevel courseLevel;

    @ManyToOne
    @JoinColumn(name = "course_status_id", nullable = false)
    private CourseStatus courseStatus;

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
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    private CourseDetail courseDetail;

    @OneToOne(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true
    )
    private CourseMedia courseMedia;

    @OneToOne(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true
    )
    private CourseSupport courseSupport;

    @OneToOne(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true
    )
    private CoursePricing coursePricing;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseMessage> courseMessages = new ArrayList<>();

    public void addMessage(CourseMessageType courseMessageType, String message){
        CourseMessage courseMessage = CourseMessage.builder()
                .courseMessageType(courseMessageType)
                .message(message)
                .build();
        this.courseMessages.add(courseMessage);
    }

    public void addMessage(CourseMessage courseMessage){
        this.courseMessages.add(courseMessage);
    }

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseRequirement> courseRequirements = new ArrayList<>();

    public void addRequirement(String requirement){
        CourseRequirement courseRequirement = CourseRequirement.builder()
                .requirement(requirement)
                .course(this)
                .build();

        this.courseRequirements.add(courseRequirement);
    }

    public void addRequirement(CourseRequirement courseRequirement){

        courseRequirement.setCourse(this);
        this.courseRequirements.add(courseRequirement);
    }


    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseLearningOutcome> courseLearningOutcomes= new ArrayList<>();

    public void addLearningOutcome(String outcome){
        CourseLearningOutcome courseLearningOutcome = CourseLearningOutcome.builder()
                .outcome(outcome)
                .course(this)
                .build();

        this.courseLearningOutcomes.add(courseLearningOutcome);
    }

    public void addLearningOutcome(CourseLearningOutcome courseLearningOutcome){
        courseLearningOutcome.setCourse(this);
        this.courseLearningOutcomes.add(courseLearningOutcome);
    }


    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseLanguage> courseLanguages = new ArrayList<>();

    public void addLanguage(Language language){
        CourseLanguage courseLanguage = CourseLanguage.builder()
                .language(language)
                .build();
        this.courseLanguages.add(courseLanguage);
    }

    public void addLanguage(CourseLanguage courseLanguage){
        this.courseLanguages.add(courseLanguage);
    }

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseAssistantInstructor> courseAssistantInstructors= new ArrayList<>();

    public void addAssistantInstructor(User user, AssistantInstructorRole assistantInstructorRole){
        CourseAssistantInstructor courseAssistantInstructor = CourseAssistantInstructor.builder()
                .role(assistantInstructorRole)
                .build();

        this.courseAssistantInstructors.add(courseAssistantInstructor);
    }

    public void addAssistantInstructor(CourseAssistantInstructor courseAssistantInstructor){
        this.courseAssistantInstructors.add(courseAssistantInstructor);
    }

    @OneToMany(
            mappedBy = "course",
            cascade =  {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Section>sections = new HashSet<>();

    public void addSection(Section section) {
        this.sections.add(section);
        section.setCourse(this);
    }

}