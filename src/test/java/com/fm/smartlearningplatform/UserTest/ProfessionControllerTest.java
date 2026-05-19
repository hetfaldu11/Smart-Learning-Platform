package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.ProfessionController;
import com.fm.smartlearningplatform.dto.user.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.ProfessionService;
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

@WebMvcTest(ProfessionController.class)
class ProfessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfessionService professionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createProfession_Returns201() throws Exception {
        CreateProfessionRequest request = new CreateProfessionRequest("Student");
        ProfessionResponse response = new ProfessionResponse(1L, "Student");

        when(professionService.createProfession(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/professions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Student"));
    }

    @Test
    void createProfession_Returns400_WhenNameBlank() throws Exception {
        CreateProfessionRequest request = new CreateProfessionRequest("");

        mockMvc.perform(post("/api/v1/professions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProfession_Returns409_WhenDuplicate() throws Exception {
        CreateProfessionRequest request = new CreateProfessionRequest("Student");

        when(professionService.createProfession(any()))
                .thenThrow(new DuplicateResourceException("Profession already exists."));

        mockMvc.perform(post("/api/v1/professions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Profession already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateProfession_Returns200() throws Exception {
        UpdateProfessionRequest request = new UpdateProfessionRequest("Web developer");
        ProfessionResponse response = new ProfessionResponse(1L, "Web developer");

        when(professionService.updateProfession(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/professions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Web developer"));
    }

    @Test
    void updateProfession_Returns404_WhenNotFound() throws Exception {
        UpdateProfessionRequest request = new UpdateProfessionRequest("Web developer");

        when(professionService.updateProfession(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Profession not found."));

        mockMvc.perform(put("/api/v1/professions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Profession not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getProfession_Returns200() throws Exception {
        ProfessionResponse response = new ProfessionResponse(1L, "Student");

        when(professionService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/professions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Student"));
    }

    @Test
    void getProfession_Returns404_WhenNotFound() throws Exception {
        when(professionService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Profession not found."));

        mockMvc.perform(get("/api/v1/professions/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Profession not found."));
    }

    @Test
    void getAllProfessions_Returns200() throws Exception {
        List<ProfessionResponse> response = List.of(
                new ProfessionResponse(1L, "Student"),
                new ProfessionResponse(2L, "Web developer")
        );

        when(professionService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/professions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Student"))
                .andExpect(jsonPath("$[1].name").value("Web developer"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteProfession_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/professions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProfession_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Profession not found."))
                .when(professionService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/professions/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Profession not found."));
    }
}