package com.fm.smartlearningplatform.model.course;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "certification_available")
    @Builder.Default
    private boolean certificationAvailable = false;

    @Column(name = "has_assignments")
    @Builder.Default
    private boolean hasAssignment = false;

    @Column(name = "has_project")
    @Builder.Default
    private boolean hasProject = false;

    @Column(name = "has_quiz")
    @Builder.Default
    private boolean hasQuiz = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

