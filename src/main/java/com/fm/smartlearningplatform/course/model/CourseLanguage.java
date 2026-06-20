package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import com.fm.smartlearningplatform.user.model.Language;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "course_languages",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_language",
                        columnNames = {"course_id", "language_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseLanguage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "is_primary")
    @Builder.Default
    private boolean primary = false;
}