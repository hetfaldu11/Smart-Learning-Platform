package com.fm.smartlearningplatform.course.dto.currency.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCurrencyRequest(

        @Size(
                min = 2,
                max = 100,
                message = "Currency name must be between 2 and 100 characters."
        )
        String name,

        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Currency code must contain exactly 3 uppercase letters."
        )
        String code

) {

    public UpdateCurrencyRequest {

        if (name != null) {

            name = name
                    .trim()
                    .replaceAll("\\s+", " ");

            if (name.isBlank()) {
                name = null;
            }
        }

        if (code != null) {

            code = code
                    .trim()
                    .toUpperCase();

            if (code.isBlank()) {
                code = null;
            }
        }
    }
}