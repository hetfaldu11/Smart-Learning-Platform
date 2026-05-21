// UserSkillServiceTest.java
package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.UserSkillMapper;
import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSkill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import com.fm.smartlearningplatform.service.user.UserSkillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSkillServiceTest {

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private UserSkillMapper userSkillMapper;

    @InjectMocks
    private UserSkillService userSkillService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createUserSkill_Success() {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, 1L);
        User user = User.builder().id(1L).build();
        Skill skill = Skill.builder().id(1L).name("Java").build();
        UserSkill userSkill = UserSkill.builder().id(1L).user(user).skill(skill).build();
        UserSkillResponse response = new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now());

        when(userSkillRepository.existsByUserIdAndSkillId(1L, 1L)).thenReturn(false);
        when(userRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(user));
        when(skillRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(skill));
        when(userRepository.save(user)).thenReturn(user);
        when(userSkillMapper.toResponse(any())).thenReturn(response);

        UserSkillResponse result = userSkillService.createUserSkill(request);

        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getSkillId()).isEqualTo(1L);
        assertThat(result.getSkillName()).isEqualTo("Java");
    }

    @Test
    void createUserSkill_ThrowsDuplicate() {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, 1L);

        when(userSkillRepository.existsByUserIdAndSkillId(1L, 1L)).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> userSkillService.createUserSkill(request));
    }

    @Test
    void createUserSkill_ThrowsNotFound_WhenUserNotFound() {
        CreateUserSkillRequest request = new CreateUserSkillRequest(99L, 1L);

        when(userSkillRepository.existsByUserIdAndSkillId(99L, 1L)).thenReturn(false);
        when(userRepository.findByIdAndDeletedAtIsNull(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.createUserSkill(request));
    }

    @Test
    void createUserSkill_ThrowsNotFound_WhenSkillNotFound() {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, 99L);
        User user = User.builder().id(1L).build();

        when(userSkillRepository.existsByUserIdAndSkillId(1L, 99L)).thenReturn(false);
        when(userRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(user));
        when(skillRepository.findByIdAndDeletedAtIsNull(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.createUserSkill(request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByUserId_Success() {
        User user = User.builder().id(1L).build();
        Skill skill = Skill.builder().id(1L).name("Java").build();
        UserSkill userSkill = UserSkill.builder().id(1L).user(user).skill(skill).build();
        UserSkillResponse response = new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now());

        when(userRepository.existsByIdAndDeletedAtIsNull(1L)).thenReturn(true);
        when(userSkillRepository.findByUserId(1L)).thenReturn(List.of(userSkill));
        when(userSkillMapper.toResponse(userSkill)).thenReturn(response);

        List<UserSkillResponse> result = userSkillService.findByUserId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSkillName()).isEqualTo("Java");
    }

    @Test
    void findByUserId_ThrowsNotFound_WhenUserNotFound() {
        when(userRepository.existsByIdAndDeletedAtIsNull(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.findByUserId(99L));
    }

    @Test
    void findBySkillId_Success() {
        User user = User.builder().id(1L).build();
        Skill skill = Skill.builder().id(1L).name("Java").build();
        UserSkill userSkill = UserSkill.builder().id(1L).user(user).skill(skill).build();
        UserSkillResponse response = new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now());

        when(skillRepository.existsByIdAndDeletedAtIsNull(1L)).thenReturn(true);
        when(userSkillRepository.findBySkillId(1L)).thenReturn(List.of(userSkill));
        when(userSkillMapper.toResponse(userSkill)).thenReturn(response);

        List<UserSkillResponse> result = userSkillService.findBySkillId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
    }

    @Test
    void findBySkillId_ThrowsNotFound_WhenSkillNotFound() {
        when(skillRepository.existsByIdAndDeletedAtIsNull(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.findBySkillId(99L));
    }

    @Test
    void findByUserIdAndSkillId_Success() {
        User user = User.builder().id(1L).build();
        Skill skill = Skill.builder().id(1L).name("Java").build();
        UserSkill userSkill = UserSkill.builder().id(1L).user(user).skill(skill).build();
        UserSkillResponse response = new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now());

        when(userRepository.existsByIdAndDeletedAtIsNull(1L)).thenReturn(true);
        when(skillRepository.existsByIdAndDeletedAtIsNull(1L)).thenReturn(true);
        when(userSkillRepository.findByUserIdAndSkillId(1L, 1L)).thenReturn(Optional.of(userSkill));
        when(userSkillMapper.toResponse(userSkill)).thenReturn(response);

        UserSkillResponse result = userSkillService.findByUserIdAndSkillId(1L, 1L);

        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getSkillId()).isEqualTo(1L);
        assertThat(result.getSkillName()).isEqualTo("Java");
    }

    @Test
    void findByUserIdAndSkillId_ThrowsNotFound_WhenUserNotFound() {
        when(userRepository.existsByIdAndDeletedAtIsNull(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.findByUserIdAndSkillId(99L, 1L));
    }

    @Test
    void findByUserIdAndSkillId_ThrowsNotFound_WhenSkillNotFound() {
        when(userRepository.existsByIdAndDeletedAtIsNull(1L)).thenReturn(true);
        when(skillRepository.existsByIdAndDeletedAtIsNull(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.findByUserIdAndSkillId(1L, 99L));
    }

    @Test
    void findByUserIdAndSkillId_ThrowsNotFound_WhenUserSkillNotFound() {
        when(userRepository.existsByIdAndDeletedAtIsNull(1L)).thenReturn(true);
        when(skillRepository.existsByIdAndDeletedAtIsNull(2L)).thenReturn(true);
        when(userSkillRepository.findByUserIdAndSkillId(1L, 2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.findByUserIdAndSkillId(1L, 2L));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        User user = User.builder().id(1L).build();
        Skill skill = Skill.builder().id(1L).name("Java").build();
        UserSkill userSkill = UserSkill.builder().id(1L).user(user).skill(skill).build();

        when(userSkillRepository.findById(1L)).thenReturn(Optional.of(userSkill));

        userSkillService.deleteById(1L);

        verify(userSkillRepository).delete(userSkill);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(userSkillRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userSkillService.deleteById(99L));
    }
}