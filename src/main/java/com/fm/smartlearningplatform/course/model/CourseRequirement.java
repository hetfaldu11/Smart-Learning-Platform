package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "course_requirements",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_requirement",
                        columnNames = {"course_id", "requirement"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequirement extends Auditable {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "requirement", nullable = false, columnDefinition = "TEXT")
    private String requirement;

}