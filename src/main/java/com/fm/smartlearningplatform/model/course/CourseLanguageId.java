package com.fm.smartlearningplatform.model.course;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseLanguageId implements Serializable {

    private Long course;

    private Long language;
}