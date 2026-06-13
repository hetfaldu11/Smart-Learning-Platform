package com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.response;

import java.time.LocalDateTime;

public record CourseAssistantInstructorResponse(

        Long id,

        Long courseId,


        Long instructorId,


        Long assistantInstructorRoleId


) {
}