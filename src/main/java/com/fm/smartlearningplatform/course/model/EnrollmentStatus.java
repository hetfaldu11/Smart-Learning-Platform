package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.common.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enrollment_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentStatus extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


}
