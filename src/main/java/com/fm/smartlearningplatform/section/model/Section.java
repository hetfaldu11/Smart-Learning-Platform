package com.fm.smartlearningplatform.section.model;

import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.course.model.Course;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sections", indexes = {@Index(name = "idx_section_course", columnList = "course_id")}, uniqueConstraints = {@UniqueConstraint(name = "uk_course_section_position", columnNames = {"course_id", "position"})})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Section extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer position;

    @Builder.Default
    @Column(name = "is_free_preview", nullable = false)
    private Boolean freePreview = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean published = true;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}