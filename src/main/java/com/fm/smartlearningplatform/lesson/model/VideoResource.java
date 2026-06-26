package com.fm.smartlearningplatform.lesson.model;

import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.lesson.model.VideoLesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "video_resources",
        indexes = {
                @Index(
                        name = "idx_video_resource_video_lesson",
                        columnList = "video_lesson_id"
                ),
                @Index(
                        name = "idx_video_resource_file",
                        columnList = "file_id"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_video_resource",
                        columnNames = {
                                "video_lesson_id",
                                "file_id"
                        }
                ),
                @UniqueConstraint(
                        name = "uk_video_resource_position",
                        columnNames = {
                                "video_lesson_id",
                                "position"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResource extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "video_lesson_id",
            nullable = false
    )
    private VideoLesson videoLesson;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "file_id",
            nullable = false
    )
    private File file;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "display_name", length = 200)
    private String displayName;
}