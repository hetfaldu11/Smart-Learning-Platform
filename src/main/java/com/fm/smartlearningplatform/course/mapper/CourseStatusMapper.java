package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseStatus.request.CreateCourseStatusRequest;
import com.fm.smartlearningplatform.course.dto.courseStatus.request.UpdateCourseStatusRequest;
import com.fm.smartlearningplatform.course.dto.courseStatus.response.CourseStatusResponse;
import com.fm.smartlearningplatform.course.model.CourseStatus;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseStatusMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)
    CourseStatus toEntity(
            CreateCourseStatusRequest request
    );



    CourseStatusResponse toResponse(
            CourseStatus courseStatus
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)

    void update(
            UpdateCourseStatusRequest request,
            @MappingTarget CourseStatus courseStatus
    );

}