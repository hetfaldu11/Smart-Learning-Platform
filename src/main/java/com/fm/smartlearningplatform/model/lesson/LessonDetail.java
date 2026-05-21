package com.fm.smartlearningplatform.model.lesson;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDetail {

    @Id
    @Column(name = "lesson_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name="transcript", columnDefinition = "TEXT")
    private String transcript;

    @Column(name= "subtitle_url")
    private String subtitleUrl;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
