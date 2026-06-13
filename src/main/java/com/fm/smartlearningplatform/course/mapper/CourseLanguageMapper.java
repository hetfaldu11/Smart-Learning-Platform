package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.courseLanguage.request.CreateCourseLanguageRequest;
import com.fm.smartlearningplatform.course.dto.courseLanguage.response.CourseLanguageResponse;
import com.fm.smartlearningplatform.course.model.CourseLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseLanguageMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "language", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CourseLanguage toEntity(
            CreateCourseLanguageRequest request
    );



    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")

    @Mapping(target = "languageId", source = "language.id")
    @Mapping(target = "languageName", source = "language.name")
    CourseLanguageResponse toResponse(
            CourseLanguage courseLanguage
    );

}