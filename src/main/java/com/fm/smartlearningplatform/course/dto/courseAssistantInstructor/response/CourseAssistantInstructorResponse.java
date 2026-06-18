package com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.response;

import java.time.LocalDateTime;

public record CourseAssistantInstructorResponse(

        Long courseId,
        String courseTitle,

        Long instructorId,
        String instructorName,

        Long assistantInstructorRoleId,
        String assistantInstructorRoleName

) {}