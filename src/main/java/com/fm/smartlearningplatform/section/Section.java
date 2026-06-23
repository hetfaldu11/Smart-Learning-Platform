package com.fm.smartlearningplatform.section;

import com.fm.smartlearningplatform.common.model.Auditable;
import com.fm.smartlearningplatform.course.model.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "section",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_section_position",
                        columnNames = {"course_id", "position"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer position;

    @Builder.Default
    @Column(name = "is_published", nullable = false)
    private boolean published = false;
}