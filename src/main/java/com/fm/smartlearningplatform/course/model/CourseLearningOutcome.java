package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(
        name = "course_learning_outcomes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_outcome",
                        columnNames = {"course_id", "outcome"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
CourseLearningOutcome  extends Auditable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "outcome", nullable = false)
    private String outcome;

    @Column(name = "display_order")
    private Integer displayOrder;


}
