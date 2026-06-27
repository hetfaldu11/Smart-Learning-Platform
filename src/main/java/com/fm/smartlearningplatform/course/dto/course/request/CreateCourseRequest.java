package com.fm.smartlearningplatform.course.dto.course.request;

import com.fm.smartlearningplatform.course.model.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseRequest(

        @NotNull(message = "Instructor id is required.")
        Long instructorId,

        @NotBlank(message = "Course title is required.")
        @Size(
                min = 3,
                max = 255,
                message = "Course title must be between 3 and 255 characters."
        )
        String title,

        @NotBlank(message = "Course subtitle is required.")
        @Size(
                min = 5,
                max = 500,
                message = "Course subtitle must be between 5 and 500 characters."
        )
        String subtitle,

        @NotNull(message = "Course level id is required.")
        Long courseLevelId,

        @NotNull(message = "Course status id is required.")
        CourseStatus courseStatus

) {

    public CreateCourseRequest {

        if (title != null) {
            title = title.trim().replaceAll("\\s+", " ")
                    .toLowerCase();
        }

        if (subtitle != null) {
            subtitle = subtitle.trim().replaceAll("\\s+", " ")
                    .toLowerCase();
        }
    }
}