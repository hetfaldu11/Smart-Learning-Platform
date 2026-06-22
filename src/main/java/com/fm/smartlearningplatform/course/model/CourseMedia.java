package com.fm.smartlearningplatform.course.model;

import com.fm.smartlearningplatform.common.model.Auditable;
import com.fm.smartlearningplatform.common.model.File;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseMedia  extends Auditable {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "thumbnail_id")
    private File thumbnail;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "promotional_lesson_id")
    private File promotionalLesson;


    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "certificate_template_public_id")
    private File certificateTemplate;

}
