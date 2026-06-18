package com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateCourseLearningOutcomeRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotBlank(message = "Course learning outcome is required.")
        @Size(
                min = 3,
                max = 500,
                message = "Course learning outcome must be between 3 and 500 characters."
        )
        String outcome,

        @PositiveOrZero(message = "Display order must be greater than or equal to 0.")
        @NotNull(message = "displayOrder is required.")
        Integer displayOrder

) {

    public CreateCourseLearningOutcomeRequest {

        if (outcome != null) {
            outcome = outcome
                    .trim()
                    .replaceAll("\\s+", " ");
        }
    }
}