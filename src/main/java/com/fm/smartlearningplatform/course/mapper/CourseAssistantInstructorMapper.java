package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.request.CreateCourseAssistantInstructorRequest;
import com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.response.CourseAssistantInstructorResponse;
import com.fm.smartlearningplatform.course.model.CourseAssistantInstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseAssistantInstructorMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "assistantInstructorRole", ignore = true)

//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
    CourseAssistantInstructor toEntity(
            CreateCourseAssistantInstructorRequest request
    );

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")

    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(target = "instructorName", source = "instructor.email")

    @Mapping(target = "assistantInstructorRoleId",
            source = "assistantInstructorRole.id")
    @Mapping(target = "assistantInstructorRoleName",
            source = "assistantInstructorRole.name")

    CourseAssistantInstructorResponse toResponse(
            CourseAssistantInstructor courseAssistantInstructor
    );

}