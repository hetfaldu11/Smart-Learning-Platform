package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseSupport.request.CreateCourseSupportRequest;
import com.fm.smartlearningplatform.course.dto.courseSupport.request.UpdateCourseSupportRequest;
import com.fm.smartlearningplatform.course.dto.courseSupport.response.CourseSupportResponse;
import com.fm.smartlearningplatform.course.model.CourseSupport;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseSupportMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)

    CourseSupport toEntity(
            CreateCourseSupportRequest request
    );

    @Mapping(target = "courseId", source = "course.id")
    CourseSupportResponse toResponse(
            CourseSupport courseSupport
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)


    void update(
            UpdateCourseSupportRequest request,
            @MappingTarget CourseSupport courseSupport
    );

}