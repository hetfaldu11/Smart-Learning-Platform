package com.fm.smartlearningplatform.course.dto.courseRequirement.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseRequirementRequest(

        @Size(
                min = 3,
                max = 500,
                message = "Course requirement must be between 3 and 500 characters."
        )
        String requirement

) {

    public UpdateCourseRequirementRequest {

        if (requirement != null) {

            requirement = requirement
                    .trim()
                    .replaceAll("\\s+", " ");

            if (requirement.isBlank()) {
                requirement = null;
            }
        }
    }
}