package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import com.github.benmanes.caffeine.cache.LoadingCache;
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
public class CourseMedia  extends Auditable {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "thumbnail_url", length = 1000)
    private String thumbnailUrl;

    @Column(name = "thumbnail_public_id", length = 255)
    private String thumbnailPublicId;

    @Column(name = "promotional_lesson_url", length = 1000)
    private String promotionalLessonUrl;
    @Column(name = "promotional_lesson_id", length = 255)
    private String promotionalLessonPublicId;

    @Column(name = "certificate_template_url", length = 1000)
    private String certificateTemplateUrl;
    @Column(name = "certificate_template_public_id", length = 255)
    private String certificateTemplatePublicId;

}
