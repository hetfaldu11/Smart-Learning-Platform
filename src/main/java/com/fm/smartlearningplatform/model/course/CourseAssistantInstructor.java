package com.fm.smartlearningplatform.model.course;

import com.fm.smartlearningplatform.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_assistant_instructors",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_assistant_instructor",
                        columnNames = {"course_id", "user_id", "role_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseAssistantInstructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "instructor_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name= "role_id", nullable = false)
    private AssistantInstructorRole role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}