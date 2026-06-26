package com.fm.smartlearningplatform.lesson.mapper;



import com.fm.smartlearningplatform.lesson.dto.lessonProgress.request.CreateLessonProgressRequest;
import com.fm.smartlearningplatform.lesson.dto.lessonProgress.request.UpdateLessonProgressRequest;
import com.fm.smartlearningplatform.lesson.dto.lessonProgress.response.LessonProgressResponse;
import com.fm.smartlearningplatform.lesson.model.LessonProgress;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LessonProgressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    LessonProgress toEntity(
            CreateLessonProgressRequest request
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    void update(
            UpdateLessonProgressRequest request,
            @MappingTarget LessonProgress lessonProgress
    );

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonTitle", source = "lesson.title")
    @Mapping(
            target = "lessonDurationSeconds",
            source = "lesson.durationSeconds"
    )
//    @Mapping(
//            target = "progressPercentage",
//            source = "progressPercentage"
//    )
    LessonProgressResponse toResponse(
            LessonProgress lessonProgress
    );


}
