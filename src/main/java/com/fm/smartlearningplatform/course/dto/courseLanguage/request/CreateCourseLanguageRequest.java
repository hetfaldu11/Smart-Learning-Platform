package com.fm.smartlearningplatform.course.dto.courseLanguage.request;

import jakarta.validation.constraints.NotNull;

public record CreateCourseLanguageRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotNull(message = "Language id is required.")
        Long languageId,

        Boolean isPrimary

) {
}