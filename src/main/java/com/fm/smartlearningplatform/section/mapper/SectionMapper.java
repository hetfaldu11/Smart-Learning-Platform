package com.fm.smartlearningplatform.section.mapper;

import com.fm.smartlearningplatform.section.dto.section.request.CreateSectionRequest;
import com.fm.smartlearningplatform.section.dto.section.request.UpdateSectionRequest;
import com.fm.smartlearningplatform.section.dto.section.response.SectionResponse;
import com.fm.smartlearningplatform.section.model.Section;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SectionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
//    @Mapping(target = "durationSeconds", ignore = true)
    @Mapping(target = "published", constant = "false")
    @Mapping(target = "deletedAt", ignore = true)
    Section toEntity(CreateSectionRequest request);

    @Mapping(target = "courseId", source = "course.id")
    SectionResponse toResponse(Section section);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
//    @Mapping(target = "durationSeconds", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void update(UpdateSectionRequest request, @MappingTarget Section section);
}
