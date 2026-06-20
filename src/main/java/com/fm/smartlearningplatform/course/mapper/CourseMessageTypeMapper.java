package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseMessageType.request.CreateCourseMessageTypeRequest;
import com.fm.smartlearningplatform.course.dto.courseMessageType.request.UpdateCourseMessageTypeRequest;
import com.fm.smartlearningplatform.course.dto.courseMessageType.response.CourseMessageTypeResponse;
import com.fm.smartlearningplatform.course.model.CourseMessageType;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMessageTypeMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)
    CourseMessageType toEntity(
            CreateCourseMessageTypeRequest request
    );


    CourseMessageTypeResponse toResponse(
            CourseMessageType courseMessageType
    );


    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)
    void update(
            UpdateCourseMessageTypeRequest request,
            @MappingTarget CourseMessageType courseMessageType
    );

}