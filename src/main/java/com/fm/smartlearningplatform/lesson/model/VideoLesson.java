package com.fm.smartlearningplatform.lesson.model;

import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.lesson.model.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "video_lessons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoLesson extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "lesson_id",
            nullable = false,
            unique = true
    )
    private Lesson lesson;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_file_id", nullable = false)
    private File video;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_file_id")
    private File thumbnail;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;
}