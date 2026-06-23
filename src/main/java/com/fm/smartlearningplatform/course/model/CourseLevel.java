package com.fm.smartlearningplatform.course.model;


import com.fm.smartlearningplatform.common.model.DateAudit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_levels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseLevel  extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name",nullable = false,unique = true)
    private String name;

    @Column(name= "deleted_at")
    private LocalDateTime deletedAt;
}