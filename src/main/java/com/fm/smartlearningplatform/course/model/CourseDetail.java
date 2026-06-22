package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.common.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail  extends Auditable {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "requirement", nullable = false, columnDefinition = "TEXT")
    private String requirement;

    @Column(name = "learning_outcome", nullable = false, columnDefinition = "TEXT")
    private String learningOutcome;

    @Column(name = "certification_available")
    @Builder.Default
    private boolean hasCertificate = false;

    @Column(name = "has_assignments")
    @Builder.Default
    private boolean hasAssignment = false;

    @Column(name = "has_project")
    @Builder.Default
    private boolean hasProject = false;

    @Column(name = "has_quiz")
    @Builder.Default
    private boolean hasQuiz = false;

}

