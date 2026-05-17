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
    private boolean certificationAvailable = false;

    @Column(name = "has_assignments")
    private boolean hasAssignment = false;

    @Column(name = "has_project")
    private boolean hasProject = false;

    @Column(name = "has_quiz")
    private boolean hasQuiz = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

