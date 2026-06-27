package com.fm.smartlearningplatform.course.dto.course.response;

import com.fm.smartlearningplatform.course.model.CourseStatus;

public record CourseResponse(

        Long id,

        Long instructorId,
        String instructorName,

        String title,
        String subtitle,

        Long courseLevelId,
        String courseLevelName,

        CourseStatus courseStatus


) {
}