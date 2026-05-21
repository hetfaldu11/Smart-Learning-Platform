package com.fm.smartlearningplatform.model.lesson;

import com.fm.smartlearningplatform.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "title",nullable = false)
    private String title;

    @Column(name="description",nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name="provider_id")
    private User user;

    @Column(name="duration_time_seconds", nullable = false)
    private Long durationTimeSeconds;

    @ManyToOne
    @JoinTable(name = "lesson_type_id")
    private LessonType lessonType;

    @Column(name="file_size",nullable = false)
    private Long fileSize;

    @CreationTimestamp
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name= "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(
            mappedBy = "lesson",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    private LessonDetail lessonDetail;

    @OneToOne(
            mappedBy = "lesson",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    private LessonMedia lessonMedia;

    @OneToMany(
            mappedBy = "lesson",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<LessonCaption> lessonCaptions = new ArrayList<>();

    public void addCaption(LessonCaption lessonCaption){
        this.lessonCaptions.add(lessonCaption);
    }

    @OneToMany(
            mappedBy = "lesson",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<LessonResource> lessonResources = new ArrayList<>();

    public void addResource(LessonResource lessonResource){
        this.lessonResources.add(lessonResource);
    }
}
