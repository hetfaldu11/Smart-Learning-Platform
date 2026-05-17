package com.fm.smartlearningplatform.model.course;

import com.fm.smartlearningplatform.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle", length = 300)
    private String subtitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private CourseDetail courseDetail;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CourseMedia courseMedia;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CourseSupport courseSupport;

    @OneToOne(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CoursePricing coursePricing;
}