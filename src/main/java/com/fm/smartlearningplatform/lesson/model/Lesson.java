package com.fm.smartlearningplatform.lesson.model;

import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import com.fm.smartlearningplatform.section.model.Section;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "lessons",
        indexes = {
                @Index(name = "idx_lesson_section", columnList = "section_id"),
                @Index(name = "idx_lesson_status", columnList = "status"),
                @Index(name = "idx_lesson_deleted_at", columnList = "deleted_at")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_section_position",
                        columnNames = {"section_id", "position"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonType type;

    @Builder.Default
    @Column(name = "is_preview", nullable = false)
    private Boolean preview = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LessonStatus status = LessonStatus.DRAFT;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}