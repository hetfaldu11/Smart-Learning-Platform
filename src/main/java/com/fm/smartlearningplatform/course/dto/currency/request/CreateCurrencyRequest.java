package com.fm.smartlearningplatform.course.dto.currency.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCurrencyRequest(

        @NotBlank(message = "Currency name is required.")
        @Size(
                min = 2,
                max = 100,
                message = "Currency name must be between 2 and 100 characters."
        )
        String name,

        @NotBlank(message = "Currency code is required.")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Currency code must contain exactly 3 uppercase letters."
        )
        String code

) {

    public CreateCurrencyRequest {

        if (name != null) {
            name = name
                    .trim()
                    .replaceAll("\\s+", " ");
        }

        if (code != null) {
            code = code
                    .trim()
                    .toUpperCase();
        }
    }
}