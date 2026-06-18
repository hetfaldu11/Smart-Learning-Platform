package com.fm.smartlearningplatform.course.dto.courseRequirement.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseRequirementRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotBlank(message = "Course requirement is required.")
        @Size(
                min = 3,
                max = 500,
                message = "Course requirement must be between 3 and 500 characters."
        )
        String requirement

) {

    public CreateCourseRequirementRequest {

        if (requirement != null) {
            requirement = requirement
                    .trim()
                    .replaceAll("\\s+", " ");
        }
    }
}