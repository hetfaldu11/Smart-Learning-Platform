package com.fm.smartlearningplatform.course.dto.courseSupport.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCourseSupportRequest(

        @Email(message = "Invalid support email format.")
        @Size(
                max = 255,
                message = "Support email must not exceed 255 characters."
        )
        String supportEmail,

        @Pattern(
                regexp = "^[0-9]{10,15}$",
                message = "Support phone number must contain 10 to 15 digits."
        )
        String supportPhone

) {

    public UpdateCourseSupportRequest {

        if (supportEmail != null) {

            supportEmail = supportEmail
                    .trim()
                    .toLowerCase();

            if (supportEmail.isBlank()) {
                supportEmail = null;
            }
        }

        if (supportPhone != null) {

            supportPhone = supportPhone
                    .trim()
                    .replaceAll("\\s+", "");

            if (supportPhone.isBlank()) {
                supportPhone = null;
            }
        }
    }
}