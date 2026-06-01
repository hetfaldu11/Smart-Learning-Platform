package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.user.model.UserSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSkillMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "skillId", source = "skill.id")
    @Mapping(target = "skillName", source = "skill.name")
    UserSkillResponse toResponse(UserSkill userSkill);
}
