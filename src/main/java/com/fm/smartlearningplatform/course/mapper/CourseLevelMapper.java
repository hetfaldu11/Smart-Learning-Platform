package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseLevel.request.CreateCourseLevelRequest;
import com.fm.smartlearningplatform.course.dto.courseLevel.request.UpdateCourseLevelRequest;
import com.fm.smartlearningplatform.course.dto.courseLevel.response.CourseLevelResponse;
import com.fm.smartlearningplatform.course.model.CourseLevel;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseLevelMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)
    CourseLevel toEntity(
            CreateCourseLevelRequest request
    );


    CourseLevelResponse toResponse(
            CourseLevel courseLevel
    );


    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)
    void update(
            UpdateCourseLevelRequest request,
            @MappingTarget CourseLevel courseLevel
    );

}