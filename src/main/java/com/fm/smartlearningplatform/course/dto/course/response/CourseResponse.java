package com.fm.smartlearningplatform.course.dto.course.response;

public record CourseResponse(

        Long id,

        Long instructorId,
        String instructorName,

        String title,
        String subtitle,

        Long courseLevelId,
        String courseLevelName,

        Long courseStatusId,
        String courseStatusName


) {
}