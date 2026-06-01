package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.user.model.Skill;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserSkill;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T15:04:23+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class UserSkillMapperImpl implements UserSkillMapper {

    @Override
    public UserSkillResponse toResponse(UserSkill userSkill) {
        if ( userSkill == null ) {
            return null;
        }

        Long userId = null;
        Long skillId = null;
        String skillName = null;

        userId = userSkillUserId( userSkill );
        skillId = userSkillSkillId( userSkill );
        skillName = userSkillSkillName( userSkill );

        UserSkillResponse userSkillResponse = new UserSkillResponse( userId, skillId, skillName );

        return userSkillResponse;
    }

    private Long userSkillUserId(UserSkill userSkill) {
        User user = userSkill.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private Long userSkillSkillId(UserSkill userSkill) {
        Skill skill = userSkill.getSkill();
        if ( skill == null ) {
            return null;
        }
        return skill.getId();
    }

    private String userSkillSkillName(UserSkill userSkill) {
        Skill skill = userSkill.getSkill();
        if ( skill == null ) {
            return null;
        }
        return skill.getName();
    }
}
