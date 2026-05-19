package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.model.user.UserSkill;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSkillMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "skillId", source = "skill.id")
    @Mapping(target= "skillName", source= "skill.name")
    UserSkillResponse toResponse(UserSkill userSkill);
}
