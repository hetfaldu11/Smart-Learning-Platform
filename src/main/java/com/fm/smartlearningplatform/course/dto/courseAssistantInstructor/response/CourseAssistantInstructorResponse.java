package com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.response;

public record CourseAssistantInstructorResponse(

        Long courseId,
        String courseTitle,

        Long instructorId,
        String instructorName,

        Long assistantInstructorRoleId,
        String assistantInstructorRoleName

) {
}