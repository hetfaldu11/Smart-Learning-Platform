package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.User;
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
                        columnNames = {"course_id", "instructor_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseAssistantInstructor extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "instructor_id", nullable = false)
    private User instructor;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "instructor_role_id", nullable = false)
    private  AssistantInstructorRole assistantInstructorRole;

}