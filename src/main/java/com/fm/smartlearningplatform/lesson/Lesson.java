package com.fm.smartlearningplatform.lesson;


import com.fm.smartlearningplatform.common.model.UserDateAudit;

import com.fm.smartlearningplatform.section.model.Section;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "lesson",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_section_position",
                        columnNames = {
                                "section_id", "position"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id")
    private Section section;

    @Column(name= "title",nullable = false, length = 200)
    private String title;

    @Column(name= "description",columnDefinition = "TEXT")
    private String description;

    @Column(name ="position", nullable = false )
    private Integer position;

    @Column(name = "duration_second", nullable = false)
    private Integer durationSeconds;

    @Column(name = "is_preview", nullable = false)
    private Boolean preview;


    @Column(name = "status", nullable = false)
    private LessonStatus status;
}
