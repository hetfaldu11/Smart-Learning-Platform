package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.response.LanguageResponse;
import com.fm.smartlearningplatform.model.user.Language;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper {

    Language toEntity(CreateLanguageRequest request);

    LanguageResponse toResponse(Language language);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLanguageFromRequest(UpdateLanguageRequest request, @MappingTarget Language language);
}