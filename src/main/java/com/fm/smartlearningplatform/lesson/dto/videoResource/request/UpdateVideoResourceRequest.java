package com.fm.smartlearningplatform.lesson.dto.videoResource.request;



import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateVideoResourceRequest(

        @Positive(message = "Resource position must be greater than 0.")
        Integer position,

        @Size(
                min = 3,
                max = 200,
                message = "Display name must be between 3 and 200 characters."
        )
        String displayName

) {

    public UpdateVideoResourceRequest {

        if (displayName != null) {
            displayName = displayName
                    .trim()
                    .replaceAll("\\s+", " ");
        }
    }
}
