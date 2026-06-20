package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "course_messages",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_message_type",
                        columnNames = {"course_id", "message_type_id"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseMessage extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "message_type_id", nullable = false)
    private CourseMessageType courseMessageType;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

}

