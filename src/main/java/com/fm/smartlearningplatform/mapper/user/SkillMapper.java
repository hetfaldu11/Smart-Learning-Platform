package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.response.SkillResponse;
import com.fm.smartlearningplatform.model.user.Skill;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {

    Skill toEntity(CreateSkillRequest request);

    SkillResponse toResponse(Skill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkillFromRequest(UpdateSkillRequest request, @MappingTarget Skill skill);
}