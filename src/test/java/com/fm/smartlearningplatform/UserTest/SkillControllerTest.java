package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.SkillController;
import com.fm.smartlearningplatform.dto.user.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.response.SkillResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillService skillService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createSkill_Returns201() throws Exception {
        CreateSkillRequest request = new CreateSkillRequest("Java");
        SkillResponse response = new SkillResponse(1L, "Java");

        when(skillService.createSkill(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void createSkill_Returns400_WhenNameBlank() throws Exception {
        CreateSkillRequest request = new CreateSkillRequest("");

        mockMvc.perform(post("/api/v1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSkill_Returns409_WhenDuplicate() throws Exception {
        CreateSkillRequest request = new CreateSkillRequest("Java");

        when(skillService.createSkill(any()))
                .thenThrow(new DuplicateResourceException("Skill already exists."));

        mockMvc.perform(post("/api/v1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Skill already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateSkill_Returns200() throws Exception {
        UpdateSkillRequest request = new UpdateSkillRequest("Python");
        SkillResponse response = new SkillResponse(1L, "Python");

        when(skillService.updateSkill(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/skills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Python"));
    }

    @Test
    void updateSkill_Returns404_WhenNotFound() throws Exception {
        UpdateSkillRequest request = new UpdateSkillRequest("Python");

        when(skillService.updateSkill(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Skill not found."));

        mockMvc.perform(put("/api/v1/skills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Skill not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getSkill_Returns200() throws Exception {
        SkillResponse response = new SkillResponse(1L, "Java");

        when(skillService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getSkill_Returns404_WhenNotFound() throws Exception {
        when(skillService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Skill not found."));

        mockMvc.perform(get("/api/v1/skills/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Skill not found."));
    }

    @Test
    void getAllSkills_Returns200() throws Exception {
        List<SkillResponse> response = List.of(
                new SkillResponse(1L, "Java"),
                new SkillResponse(2L, "Python")
        );

        when(skillService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[1].name").value("Python"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteSkill_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/skills/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSkill_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Skill not found."))
                .when(skillService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/skills/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Skill not found."));
    }
}