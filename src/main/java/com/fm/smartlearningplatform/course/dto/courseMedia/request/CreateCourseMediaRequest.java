package com.fm.smartlearningplatform.course.dto.courseMedia.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCourseMediaRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @Size(
                max = 1000,
                message = "Thumbnail URL must not exceed 1000 characters."
        )
        @Pattern(
                regexp = "^(https?://).*$",
                message = "Thumbnail URL must be a valid URL."
        )
        String thumbnailUrl,

        @Size(
                max = 1000,
                message = "Promotional lesson URL must not exceed 1000 characters."
        )
        @Pattern(
                regexp = "^(https?://).*$",
                message = "Promotional lesson URL must be a valid URL."
        )
        String promotionalLessonUrl,

        @Size(
                max = 1000,
                message = "Certificate template URL must not exceed 1000 characters."
        )
        @Pattern(
                regexp = "^(https?://).*$",
                message = "Certificate template URL must be a valid URL."
        )
        String certificateTemplateUrl

) {

    public CreateCourseMediaRequest {

        if (thumbnailUrl != null) {
            thumbnailUrl = thumbnailUrl.trim();
        }

        if (promotionalLessonUrl != null) {
            promotionalLessonUrl = promotionalLessonUrl.trim();
        }

        if (certificateTemplateUrl != null) {
            certificateTemplateUrl = certificateTemplateUrl.trim();
        }
    }
}