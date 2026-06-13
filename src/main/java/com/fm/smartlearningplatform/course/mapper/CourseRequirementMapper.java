package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseRequirement.request.CreateCourseRequirementRequest;
import com.fm.smartlearningplatform.course.dto.courseRequirement.request.UpdateCourseRequirementRequest;
import com.fm.smartlearningplatform.course.dto.courseRequirement.response.CourseRequirementResponse;
import com.fm.smartlearningplatform.course.model.CourseRequirement;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseRequirementMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CourseRequirement toEntity(
            CreateCourseRequirementRequest request
    );



    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")
    CourseRequirementResponse toResponse(
            CourseRequirement courseRequirement
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
            UpdateCourseRequirementRequest request,
            @MappingTarget CourseRequirement courseRequirement
    );

}