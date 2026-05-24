package com.fm.smartlearningplatform.model.lesson;
import com.fm.smartlearningplatform.model.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "section_lesson",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_section_lesson",
                        columnNames = {
                                "section_id", "lesson_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_order", nullable = false)
    private Integer lessonOrder;

    @Column(name = "is_preview")
    private Boolean isPreview = false;

    @Column(name = "need_revision")
    private Boolean needRevision = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false
    )
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false
    )
    private Lesson lesson;
}
