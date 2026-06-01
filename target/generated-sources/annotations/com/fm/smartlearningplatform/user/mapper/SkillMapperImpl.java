package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.response.SkillResponse;
import com.fm.smartlearningplatform.user.model.Skill;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T15:04:24+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class SkillMapperImpl implements SkillMapper {

    @Override
    public Skill toEntity(CreateSkillRequest request) {
        if ( request == null ) {
            return null;
        }

        Skill.SkillBuilder skill = Skill.builder();

        skill.name( request.name() );

        return skill.build();
    }

    @Override
    public SkillResponse toResponse(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = skill.getId();
        name = skill.getName();

        SkillResponse skillResponse = new SkillResponse( id, name );

        return skillResponse;
    }

    @Override
    public void updateSkillFromRequest(UpdateSkillRequest request, Skill skill) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            skill.setName( request.name() );
        }
    }
}
