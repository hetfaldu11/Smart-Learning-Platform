package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.response.SkillResponse;
import com.fm.smartlearningplatform.user.model.Skill;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {

    Skill toEntity(CreateSkillRequest request);

    SkillResponse toResponse(Skill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkillFromRequest(UpdateSkillRequest request, @MappingTarget Skill skill);
}