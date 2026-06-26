package com.fm.smartlearningplatform.lesson.mapper;

import com.fm.smartlearningplatform.common.mapper.FileMapper;
import com.fm.smartlearningplatform.lesson.dto.videoResource.request.CreateVideoResourceRequest;
import com.fm.smartlearningplatform.lesson.dto.videoResource.request.UpdateVideoResourceRequest;
import com.fm.smartlearningplatform.lesson.dto.videoResource.response.VideoResourceResponse;
import com.fm.smartlearningplatform.lesson.model.VideoResource;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = FileMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VideoResourceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "videoLesson", ignore = true)
    @Mapping(target = "file", ignore = true)
    VideoResource toEntity(
            CreateVideoResourceRequest request
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void update(
            UpdateVideoResourceRequest request,
            @MappingTarget VideoResource videoResource
    );

    @Mapping(target = "videoLessonId", source = "videoLesson.id")
    @Mapping(target = "lessonTitle", source = "videoLesson.lesson.title")
    @Mapping(target = "lessonId", source = "videoLesson.lesson.id")
    @Mapping(target = "file", source = "file")
    VideoResourceResponse toResponse(
            VideoResource videoResource
    );
}
