package com.fm.smartlearningplatform.course.dto.courseRequirement.response;

public record CourseRequirementResponse(

        Long id,

        Long courseId,
        String courseTitle,

        String requirement

) {
}