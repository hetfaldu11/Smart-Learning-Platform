package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.common.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_supports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSupport extends Auditable {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "support_email")
    private String supportEmail;

    @Column(name = "support_phone")
    private String supportPhone;

}