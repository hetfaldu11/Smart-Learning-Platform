package com.fm.smartlearningplatform.model.course;

import com.fm.smartlearningplatform.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_assistant_instructors")
@IdClass(CourseAssistantInstructorId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseAssistantInstructor {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "course_id")
    private Course course;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "instructor_id")
    private User user;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private InstructorRole role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}