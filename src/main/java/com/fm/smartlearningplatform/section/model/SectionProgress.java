package com.fm.smartlearningplatform.section.model;

import com.fm.smartlearningplatform.common.model.DateAudit;
import com.fm.smartlearningplatform.section.model.Section;
import com.fm.smartlearningplatform.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "section_progress",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_section",
                        columnNames = {
                                "user_id", "section_id"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionProgress extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id")
    private Section section;

    @Column(name = "completed_lessons", nullable = false)
    private Integer completedLessons;

    @Column(name = "total_lessons", nullable = false)
    private Integer totalLessons;

    @Column(name = "is_completed", nullable = false)
    @Builder.Default
    private Boolean completed = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}