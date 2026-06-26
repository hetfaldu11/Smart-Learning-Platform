package com.fm.smartlearningplatform.lesson.dto.videoResource.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateVideoResourceRequest(

        @NotNull(message = "Video lesson id is required.")
        Long videoLessonId,

        @NotNull(message = "Resource position is required.")
        @Positive(message = "Resource position must be greater than 0.")
        Integer position,

        @NotBlank(message = "Display name is required.")
        @Size(
                min = 3,
                max = 200,
                message = "Display name must be between 3 and 200 characters."
        )
        String displayName

) {

    public CreateVideoResourceRequest {

        if (displayName != null) {
            displayName = displayName
                    .trim()
                    .replaceAll("\\s+", " ");
        }
    }
}
