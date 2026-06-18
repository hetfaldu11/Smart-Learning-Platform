package com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.request;

import jakarta.validation.constraints.NotNull;

public record CreateCourseAssistantInstructorRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotNull(message = "Instructor id is required.")
        Long instructorId,

        @NotNull(message = "Assistant instructor role id is required.")
        Long assistantInstructorRoleId

) {
}