package com.fm.smartlearningplatform.model.lesson;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_resources")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResource{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "resource_type_id", nullable = false)
    private LessonResourceType lessonResourceType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "resource_url", nullable = false)
    private String resourceUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
