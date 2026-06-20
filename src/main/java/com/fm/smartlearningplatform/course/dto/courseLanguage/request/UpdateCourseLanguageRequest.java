package com.fm.smartlearningplatform.course.dto.courseLanguage.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCourseLanguageRequest(

        @NotNull(message = "Language id is required.")
        Long languageId,

        Boolean primary
) {
}
