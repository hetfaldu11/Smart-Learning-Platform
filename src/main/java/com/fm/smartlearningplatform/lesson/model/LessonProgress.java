package com.fm.smartlearningplatform.lesson.model;

import com.fm.smartlearningplatform.common.model.DateAudit;
import com.fm.smartlearningplatform.lesson.model.Lesson;
import com.fm.smartlearningplatform.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "lesson_progress",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_lesson",
                        columnNames = {
                                "user_id", "lesson_id"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgress extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Builder.Default
    @Column(name = "watched_seconds", nullable = false)
    private Integer watchedSeconds = 0;

    @Builder.Default
    @Column(name = "last_position_seconds", nullable = false)
    private Integer lastPositionSeconds = 0;

    @Builder.Default
    @Column(name = "progress_percentage", nullable = false,
            precision = 5,
            scale = 2)
    private Double progressPercentage = 0.0;

    @Builder.Default
    @Column(name = "is_completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}