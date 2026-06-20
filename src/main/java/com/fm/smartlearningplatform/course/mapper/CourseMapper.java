package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.course.request.CreateCourseRequest;
import com.fm.smartlearningplatform.course.dto.course.request.UpdateCourseRequest;
import com.fm.smartlearningplatform.course.dto.course.response.CourseResponse;
import com.fm.smartlearningplatform.course.model.Course;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "courseLevel", ignore = true)
    @Mapping(target = "courseStatus", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)

    @Mapping(target = "courseMessages", ignore = true)
//    @Mapping(target = "courseRequirements", ignore = true)
//    @Mapping(target = "courseLearningOutcomes", ignore = true)
    @Mapping(target = "courseLanguages", ignore = true)
    @Mapping(target = "assistantInstructors", ignore = true)
    Course toEntity(CreateCourseRequest request);


    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(
            target = "instructorName",
            source = "instructor.email"
    )

    @Mapping(
            target = "courseLevelId",
            source = "courseLevel.id"
    )
    @Mapping(
            target = "courseLevelName",
            source = "courseLevel.name"
    )

    @Mapping(
            target = "courseStatusId",
            source = "courseStatus.id"
    )
    @Mapping(
            target = "courseStatusName",
            source = "courseStatus.name"
    )
    CourseResponse toResponse(Course course);


    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "courseLevel", ignore = true)
    @Mapping(target = "courseStatus", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)

    @Mapping(target = "courseMessages", ignore = true)
//    @Mapping(target = "courseRequirements", ignore = true)
//    @Mapping(target = "courseLearningOutcomes", ignore = true)
    @Mapping(target = "courseLanguages", ignore = true)
    @Mapping(target = "assistantInstructors", ignore = true)
//    @Mapping(target = "sections", ignore = true)

    void update(UpdateCourseRequest request, @MappingTarget Course course);

}