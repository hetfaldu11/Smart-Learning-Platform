package com.fm.smartlearningplatform.model.lesson;

import com.fm.smartlearningplatform.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "lesson_watch_history",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_lesson",
                        columnNames = {"user_id", "lesson_id"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonWatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @CreationTimestamp
    @Column(name = "watched_at", nullable = false)
    private LocalDateTime watchedAt;

    @Column(name = "watch_duration")
    private Integer watchDuration;

    @Column(name = "device_info")
    private String deviceInfo;
}