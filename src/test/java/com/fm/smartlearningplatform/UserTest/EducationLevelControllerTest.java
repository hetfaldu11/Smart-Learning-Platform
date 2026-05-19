package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.EducationLevelController;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.EducationLevelService;
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

@WebMvcTest(EducationLevelController.class)
class EducationLevelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EducationLevelService educationLevelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createEducationLevel_Returns201() throws Exception {
        CreateEducationLevelRequest request = new CreateEducationLevelRequest("Java");
        EducationLevelResponse response = new EducationLevelResponse(1L, "Java");

        when(educationLevelService.createEducationLevel(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/educationLevels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void createEducationLevel_Returns400_WhenNameBlank() throws Exception {
        CreateEducationLevelRequest request = new CreateEducationLevelRequest("");

        mockMvc.perform(post("/api/v1/educationLevels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEducationLevel_Returns409_WhenDuplicate() throws Exception {
        CreateEducationLevelRequest request = new CreateEducationLevelRequest("Java");

        when(educationLevelService.createEducationLevel(any()))
                .thenThrow(new DuplicateResourceException("EducationLevel already exists."));

        mockMvc.perform(post("/api/v1/educationLevels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("EducationLevel already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateEducationLevel_Returns200() throws Exception {
        UpdateEducationLevelRequest request = new UpdateEducationLevelRequest("Python");
        EducationLevelResponse response = new EducationLevelResponse(1L, "Python");

        when(educationLevelService.updateEducationLevel(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/educationLevels/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Python"));
    }

    @Test
    void updateEducationLevel_Returns404_WhenNotFound() throws Exception {
        UpdateEducationLevelRequest request = new UpdateEducationLevelRequest("Python");

        when(educationLevelService.updateEducationLevel(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("EducationLevel not found."));

        mockMvc.perform(put("/api/v1/educationLevels/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("EducationLevel not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getEducationLevel_Returns200() throws Exception {
        EducationLevelResponse response = new EducationLevelResponse(1L, "Java");

        when(educationLevelService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/educationLevels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getEducationLevel_Returns404_WhenNotFound() throws Exception {
        when(educationLevelService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("EducationLevel not found."));

        mockMvc.perform(get("/api/v1/educationLevels/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("EducationLevel not found."));
    }

    @Test
    void getAllEducationLevels_Returns200() throws Exception {
        List<EducationLevelResponse> response = List.of(
                new EducationLevelResponse(1L, "Java"),
                new EducationLevelResponse(2L, "Python")
        );

        when(educationLevelService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/educationLevels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[1].name").value("Python"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteEducationLevel_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/educationLevels/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEducationLevel_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("EducationLevel not found."))
                .when(educationLevelService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/educationLevels/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("EducationLevel not found."));
    }
}