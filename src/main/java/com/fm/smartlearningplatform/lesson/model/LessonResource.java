package com.fm.smartlearningplatform.lesson.model;


import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.common.model.UserDateAudit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "lesson_resource",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_lesson_file",
                        columnNames = {
                                "lesson_id",
                                "file_id"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResource extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id")
    private File file;
}
