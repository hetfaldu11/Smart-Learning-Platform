package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.user.model.Profession;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessionMapper {

    Profession toEntity(CreateProfessionRequest request);

    ProfessionResponse toResponse(Profession profession);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfessionFromRequest(UpdateProfessionRequest request, @MappingTarget Profession profession);
}