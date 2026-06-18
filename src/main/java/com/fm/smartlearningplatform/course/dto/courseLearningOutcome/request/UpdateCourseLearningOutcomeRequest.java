package com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateCourseLearningOutcomeRequest(

        @Size(
                min = 3,
                max = 500,
                message = "Course learning outcome must be between 3 and 500 characters."
        )
        String outcome,

        @PositiveOrZero(
                message = "Display order must be greater than or equal to 0."
        )
        Integer displayOrder

) {

    public UpdateCourseLearningOutcomeRequest {

        if (outcome != null) {

            outcome = outcome
                    .trim()
                    .replaceAll("\\s+", " ");

            if (outcome.isBlank()) {
                outcome = null;
            }
        }
    }
}