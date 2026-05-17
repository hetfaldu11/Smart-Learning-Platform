package com.fm.smartlearningplatform.model.course;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseAssistantInstructorId {
    private Long course;

    private Long instructor;
}
