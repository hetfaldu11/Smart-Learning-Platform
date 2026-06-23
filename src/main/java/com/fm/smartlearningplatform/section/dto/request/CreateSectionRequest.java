package com.fm.smartlearningplatform.section.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSectionRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotBlank(message = "Section title is required.")
        @Size(
                min = 3,
                max = 200,
                message = "Section title must be between 3 and 200 characters."
        )
        String title,

        @Size(
                max = 1000,
                message = "Section description cannot exceed 1000 characters."
        )
        String description,

        @NotNull(message = "Section position is required.")
        @Min(value = 1, message = "Position must be greater than 0.")
        Integer position

) {

    public CreateSectionRequest {

        if (title != null) {
            title = title.trim().replaceAll("\\s+", " ");
        }

        if (description != null) {
            description = description.trim().replaceAll("\\s+", " ");
        }
    }
}
