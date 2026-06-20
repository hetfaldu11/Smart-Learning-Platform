package com.fm.smartlearningplatform.course.dto.courseLanguage.response;

public record CourseLanguageResponse(

        Long id,

        Long courseId,
        String courseTitle,

        Long languageId,
        String languageName,

        boolean primary

) {
}