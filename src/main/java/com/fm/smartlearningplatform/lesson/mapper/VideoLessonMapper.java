package com.fm.smartlearningplatform.lesson.mapper;



import com.fm.smartlearningplatform.common.mapper.FileMapper;
import com.fm.smartlearningplatform.lesson.dto.videoLesson.response.VideoLessonResponse;
import com.fm.smartlearningplatform.lesson.model.VideoLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = FileMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VideoLessonMapper {

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonTitle", source = "lesson.title")
//    @Mapping(target = "video", source = "video")
//    @Mapping(target = "thumbnail", source = "thumbnail")
    VideoLessonResponse toResponse(
            VideoLesson videoLesson
    );
}
