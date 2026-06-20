package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "course_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseMedia extends Auditable {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "promotional_lesson_url")
    private String promotionalLessonUrl;

    @Column(name = "certificate_template_url")
    private String certificateTemplateUrl;

    @Column(name = "deleted_At")
    private LocalDate deletedAt;
}
