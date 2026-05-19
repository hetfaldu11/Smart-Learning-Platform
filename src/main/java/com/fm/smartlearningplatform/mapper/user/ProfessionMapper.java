package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.model.user.Profession;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessionMapper {

    Profession toEntity(CreateProfessionRequest request);

    ProfessionResponse toResponse(Profession profession);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfessionFromRequest(UpdateProfessionRequest request, @MappingTarget Profession profession);
}