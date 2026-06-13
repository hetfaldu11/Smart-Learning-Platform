package com.fm.smartlearningplatform.course.dto.course.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseRequest(

        @Size(
                min = 3,
                max = 255,
                message = "Course title must be between 3 and 255 characters."
        )
        String title,

        @Size(
                min = 5,
                max = 500,
                message = "Course subtitle must be between 5 and 500 characters."
        )
        String subtitle,

        Long courseLevelId,

        Long courseStatusId

) {

    public UpdateCourseRequest {

        if (title != null) {
            title = title.trim().toLowerCase();

            if (title.isBlank()) {
                title = null;
            }
        }

        if (subtitle != null) {
            subtitle = subtitle.trim().toLowerCase();

            if (subtitle.isBlank()) {
                subtitle = null;
            }
        }
    }
}