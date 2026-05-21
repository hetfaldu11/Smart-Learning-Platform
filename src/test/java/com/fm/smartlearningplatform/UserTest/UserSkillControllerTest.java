// UserSkillControllerTest.java
package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.UserSkillController;
import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.UserSkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserSkillController.class)
class UserSkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserSkillService userSkillService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createUserSkill_Returns201() throws Exception {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, 1L);
        UserSkillResponse response = new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now());

        when(userSkillService.createUserSkill(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/user-skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.skillId").value(1L))
                .andExpect(jsonPath("$.skillName").value("Java"));
    }

    @Test
    void createUserSkill_Returns400_WhenUserIdNull() throws Exception {
        CreateUserSkillRequest request = new CreateUserSkillRequest(null, 1L);

        mockMvc.perform(post("/api/v1/user-skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserSkill_Returns400_WhenSkillIdNull() throws Exception {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, null);

        mockMvc.perform(post("/api/v1/user-skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserSkill_Returns404_WhenUserNotFound() throws Exception {
        CreateUserSkillRequest request = new CreateUserSkillRequest(99L, 1L);

        when(userSkillService.createUserSkill(any()))
                .thenThrow(new ResourceNotFoundException("User not found."));

        mockMvc.perform(post("/api/v1/user-skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found."));
    }

    @Test
    void createUserSkill_Returns404_WhenSkillNotFound() throws Exception {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, 99L);

        when(userSkillService.createUserSkill(any()))
                .thenThrow(new ResourceNotFoundException("Skill not found."));

        mockMvc.perform(post("/api/v1/user-skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Skill not found."));
    }

    @Test
    void createUserSkill_Returns409_WhenDuplicate() throws Exception {
        CreateUserSkillRequest request = new CreateUserSkillRequest(1L, 1L);

        when(userSkillService.createUserSkill(any()))
                .thenThrow(new DuplicateResourceException("UserSkill already exists."));

        mockMvc.perform(post("/api/v1/user-skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("UserSkill already exists."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByUserId_Returns200() throws Exception {
        List<UserSkillResponse> response = List.of(
                new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now()),
                new UserSkillResponse(2L, 1L, 2L, "Python", LocalDateTime.now())
        );

        when(userSkillService.findByUserId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/user-skills/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].skillName").value("Java"))
                .andExpect(jsonPath("$[1].skillName").value("Python"));
    }

    @Test
    void findByUserId_Returns404_WhenUserNotFound() throws Exception {
        when(userSkillService.findByUserId(99L))
                .thenThrow(new ResourceNotFoundException("User not found."));

        mockMvc.perform(get("/api/v1/user-skills/user/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found."));
    }

    @Test
    void findBySkillId_Returns200() throws Exception {
        List<UserSkillResponse> response = List.of(
                new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now()),
                new UserSkillResponse(2L, 2L, 1L, "Java", LocalDateTime.now())
        );

        when(userSkillService.findBySkillId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/user-skills/skill/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[1].userId").value(2L));
    }

    @Test
    void findBySkillId_Returns404_WhenSkillNotFound() throws Exception {
        when(userSkillService.findBySkillId(99L))
                .thenThrow(new ResourceNotFoundException("Skill not found."));

        mockMvc.perform(get("/api/v1/user-skills/skill/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Skill not found."));
    }

    @Test
    void findByUserIdAndSkillId_Returns200() throws Exception {
        UserSkillResponse response = new UserSkillResponse(1L, 1L, 1L, "Java", LocalDateTime.now());

        when(userSkillService.findByUserIdAndSkillId(1L, 1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/user-skills/user/1/skill/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.skillId").value(1L))
                .andExpect(jsonPath("$.skillName").value("Java"));
    }

    @Test
    void findByUserIdAndSkillId_Returns404_WhenUserNotFound() throws Exception {
        when(userSkillService.findByUserIdAndSkillId(eq(99L), eq(1L)))
                .thenThrow(new ResourceNotFoundException("User not found."));

        mockMvc.perform(get("/api/v1/user-skills/user/99/skill/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found."));
    }

    @Test
    void findByUserIdAndSkillId_Returns404_WhenSkillNotFound() throws Exception {
        when(userSkillService.findByUserIdAndSkillId(eq(1L), eq(99L)))
                .thenThrow(new ResourceNotFoundException("Skill not found."));

        mockMvc.perform(get("/api/v1/user-skills/user/1/skill/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Skill not found."));
    }

    @Test
    void findByUserIdAndSkillId_Returns404_WhenUserSkillNotFound() throws Exception {
        when(userSkillService.findByUserIdAndSkillId(eq(1L), eq(2L)))
                .thenThrow(new ResourceNotFoundException("UserSkill not found."));

        mockMvc.perform(get("/api/v1/user-skills/user/1/skill/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("UserSkill not found."));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/user-skills/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("UserSkill not found."))
                .when(userSkillService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/user-skills/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("UserSkill not found."));
    }
}