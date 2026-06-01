package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.response.LanguageResponse;
import com.fm.smartlearningplatform.user.model.Language;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper {

    Language toEntity(CreateLanguageRequest request);

    LanguageResponse toResponse(Language language);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLanguageFromRequest(UpdateLanguageRequest request, @MappingTarget Language language);
}