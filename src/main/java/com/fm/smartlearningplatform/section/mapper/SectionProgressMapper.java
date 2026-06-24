package com.fm.smartlearningplatform.section.mapper;

import com.fm.smartlearningplatform.section.dto.sectionProgress.request.CreateSectionProgressRequest;
import com.fm.smartlearningplatform.section.dto.sectionProgress.response.SectionProgressResponse;
import com.fm.smartlearningplatform.section.model.SectionProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionProgressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    SectionProgress toEntity(CreateSectionProgressRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "sectionId", source = "section.id")
    SectionProgressResponse toResponse(SectionProgress sectionProgress);
}