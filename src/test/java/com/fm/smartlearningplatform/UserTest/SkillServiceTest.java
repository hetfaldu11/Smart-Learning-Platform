package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.response.SkillResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.SkillMapper;
import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import com.fm.smartlearningplatform.service.user.SkillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private SkillMapper skillMapper;

    @InjectMocks
    private SkillService skillService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createSkill_Success() {
        CreateSkillRequest request = new CreateSkillRequest("Java");
        Skill skill = Skill.builder().id(1L).name("Java").build();
        SkillResponse response = new SkillResponse(1L, "Java");

        when(skillRepository.existsByNameAndDeletedAtIsNull("Java")).thenReturn(false);
        when(skillMapper.toEntity(request)).thenReturn(skill);
        when(skillRepository.save(skill)).thenReturn(skill);
        when(skillMapper.toResponse(skill)).thenReturn(response);

        SkillResponse result = skillService.createSkill(request);

        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void createSkill_ThrowsDuplicate() {
        CreateSkillRequest request = new CreateSkillRequest("Java");

        when(skillRepository.existsByNameAndDeletedAtIsNull("Java")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> skillService.createSkill(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateSkill_Success() {
        UpdateSkillRequest request = new UpdateSkillRequest("Python");
        Skill skill = Skill.builder().id(1L).name("Java").build();
        SkillResponse response = new SkillResponse(1L, "Python");

        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(skill));
        when(skillRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Python")).thenReturn(false);
        when(skillRepository.save(skill)).thenReturn(skill);
        when(skillMapper.toResponse(skill)).thenReturn(response);

        SkillResponse result = skillService.updateSkill(1L, request);

        assertThat(result.getName()).isEqualTo("Python");
    }

    @Test
    void updateSkill_ThrowsNotFound() {
        UpdateSkillRequest request = new UpdateSkillRequest("Python");

        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> skillService.updateSkill(1L, request));
    }

    @Test
    void updateSkill_ThrowsDuplicate() {
        UpdateSkillRequest request = new UpdateSkillRequest("Python");
        Skill skill = Skill.builder().id(1L).name("Java").build();

        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(skill));
        when(skillRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Python")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> skillService.updateSkill(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Skill skill = Skill.builder().id(1L).name("Java").build();
        SkillResponse response = new SkillResponse(1L, "Java");

        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(skill));
        when(skillMapper.toResponse(skill)).thenReturn(response);

        SkillResponse result = skillService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> skillService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Skill skill = Skill.builder().id(1L).name("Java").build();
        SkillResponse response = new SkillResponse(1L, "Java");

        when(skillRepository.findByDeletedAtIsNull()).thenReturn(List.of(skill));
        when(skillMapper.toResponse(skill)).thenReturn(response);

        List<SkillResponse> result = skillService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Java");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Skill skill = Skill.builder().id(1L).name("Java").build();

        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(skill));

        skillService.deleteById(1L);

        assertThat(skill.getDeletedAt()).isNotNull();
        verify(skillRepository).save(skill);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> skillService.deleteById(1L));
    }
}