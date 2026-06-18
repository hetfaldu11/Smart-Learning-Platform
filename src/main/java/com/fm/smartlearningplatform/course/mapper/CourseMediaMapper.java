package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseMedia.request.CreateCourseMediaRequest;
import com.fm.smartlearningplatform.course.dto.courseMedia.request.UpdateCourseMediaRequest;
import com.fm.smartlearningplatform.course.dto.courseMedia.response.CourseMediaResponse;
import com.fm.smartlearningplatform.course.model.CourseMedia;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMediaMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)

    CourseMedia toEntity(
            CreateCourseMediaRequest request
    );



    @Mapping(target = "courseId", source = "course.id")
    CourseMediaResponse toResponse(
            CourseMedia courseMedia
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)
    void update(
            UpdateCourseMediaRequest request,
            @MappingTarget CourseMedia courseMedia
    );

}