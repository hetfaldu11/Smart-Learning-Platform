package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseMessage.request.CreateCourseMessageRequest;
import com.fm.smartlearningplatform.course.dto.courseMessage.request.UpdateCourseMessageRequest;
import com.fm.smartlearningplatform.course.dto.courseMessage.response.CourseMessageResponse;
import com.fm.smartlearningplatform.course.model.CourseMessage;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMessageMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "courseMessageType", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CourseMessage toEntity(
            CreateCourseMessageRequest request
    );



    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")

    @Mapping(
            target = "courseMessageTypeId",
            source = "courseMessageType.id"
    )
    @Mapping(
            target = "courseMessageTypeName",
            source = "courseMessageType.name"
    )
    CourseMessageResponse toResponse(
            CourseMessage courseMessage
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "courseMessageType", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(
            UpdateCourseMessageRequest request,
            @MappingTarget CourseMessage courseMessage
    );

}