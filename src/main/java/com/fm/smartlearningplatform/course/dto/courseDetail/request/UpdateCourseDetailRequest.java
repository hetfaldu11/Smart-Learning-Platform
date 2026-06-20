package com.fm.smartlearningplatform.course.dto.courseDetail.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCourseDetailRequest(

        @Size(
                min = 10,
                max = 10000,
                message = "Course description must be between 10 and 10000 characters."
        )
        String description,

        @Size(
                min = 10,
                max = 10000,
                message = "Course requirement must be between 10 and 10000 characters."
        )
        String requirement,

        @Size(
                min = 10,
                max = 10000,
                message = "Course learning outcome must be between 10 and 10000 characters."
        )
        String learningOutcome,

        Boolean hasCertificate,

        Boolean hasAssignment,

        Boolean hasProject,

        Boolean hasQuiz

) {

    public UpdateCourseDetailRequest {

        if (description != null) {

            description = description
                    .trim()
                    .replaceAll("\\s+", " ");

            if (description.isBlank()) {
                description = null;
            }
        }
        if (requirement != null) {

            requirement = requirement.trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();

            if (requirement.isEmpty()) {
                requirement = null;
            }
        }

        if (learningOutcome != null) {

            learningOutcome = learningOutcome.trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();

            if (learningOutcome.isEmpty()) {
                learningOutcome = null;
            }
        }
    }
}