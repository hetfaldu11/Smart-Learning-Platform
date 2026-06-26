package com.fm.smartlearningplatform.lesson.mapper;

import com.fm.smartlearningplatform.lesson.dto.lesson.request.CreateLessonRequest;
import com.fm.smartlearningplatform.lesson.dto.lesson.request.UpdateLessonRequest;
import com.fm.smartlearningplatform.lesson.dto.lesson.response.LessonResponse;
import com.fm.smartlearningplatform.lesson.model.Lesson;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LessonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Lesson toEntity(CreateLessonRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void update(
            UpdateLessonRequest request,
            @MappingTarget Lesson lesson
    );

    @Mapping(target = "sectionId", source = "section.id")
    @Mapping(target = "sectionTitle", source = "section.title")
    LessonResponse toResponse(Lesson lesson);
}