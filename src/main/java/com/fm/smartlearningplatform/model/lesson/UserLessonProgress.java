package com.fm.smartlearningplatform.model.lesson;

import com.fm.smartlearningplatform.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_lesson_progress")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="lesson_id")
    private Lesson lesson;

    @Column(name = "completed")
    @Builder.Default
    private boolean completed = false;

    @Column(name = "watched_seconds")
    @Builder.Default
    private int watchedSeconds = 0;

    @Column(name = "last_watched_at")
    private LocalDateTime lastWatchedAt;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}