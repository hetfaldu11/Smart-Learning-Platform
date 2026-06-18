package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseDetail.request.CreateCourseDetailRequest;
import com.fm.smartlearningplatform.course.dto.courseDetail.request.UpdateCourseDetailRequest;
import com.fm.smartlearningplatform.course.dto.courseDetail.response.CourseDetailResponse;
import com.fm.smartlearningplatform.course.model.CourseDetail;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseDetailMapper {

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    CourseDetail toEntity(CreateCourseDetailRequest request);

    @Mapping(target = "courseId", source = "course.id")
    CourseDetailResponse toResponse(CourseDetail courseDetail);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    void update(
            UpdateCourseDetailRequest request,
            @MappingTarget CourseDetail courseDetail
    );

}