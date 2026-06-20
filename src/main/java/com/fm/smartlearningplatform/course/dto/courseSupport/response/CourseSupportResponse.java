package com.fm.smartlearningplatform.course.dto.courseSupport.response;

import java.time.LocalDateTime;

public record CourseSupportResponse(

        Long courseId,

        String supportEmail,

        String supportPhone

) {
}