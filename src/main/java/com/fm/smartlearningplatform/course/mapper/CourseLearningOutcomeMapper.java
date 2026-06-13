package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request.CreateCourseLearningOutcomeRequest;
import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request.UpdateCourseLearningOutcomeRequest;
import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.response.CourseLearningOutcomeResponse;
import com.fm.smartlearningplatform.course.model.CourseLearningOutcome;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseLearningOutcomeMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CourseLearningOutcome toEntity(
            CreateCourseLearningOutcomeRequest request
    );



    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")
    CourseLearningOutcomeResponse toResponse(
            CourseLearningOutcome courseLearningOutcome
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(
            UpdateCourseLearningOutcomeRequest request,
            @MappingTarget CourseLearningOutcome courseLearningOutcome
    );

}