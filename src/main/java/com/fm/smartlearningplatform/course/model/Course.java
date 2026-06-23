package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.common.model.DateAudit;
import com.fm.smartlearningplatform.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle", nullable = false)
    private String subtitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_level_id", nullable = false)
    private CourseLevel courseLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_status_id", nullable = false)
    private CourseStatus courseStatus;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseMessage> courseMessages = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseLanguage> courseLanguages = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseAssistantInstructor> assistantInstructors= new ArrayList<>();

//    @OneToMany(
//            mappedBy = "course",
//            cascade =  {CascadeType.PERSIST,CascadeType.MERGE},
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Section>sections = new ArrayList<>();

}