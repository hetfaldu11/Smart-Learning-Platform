package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.GenderController;
import com.fm.smartlearningplatform.dto.user.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.response.GenderResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.GenderService;
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

@WebMvcTest(GenderController.class)
class GenderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenderService genderService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createGender_Returns201() throws Exception {
        CreateGenderRequest request = new CreateGenderRequest("Male");
        GenderResponse response = new GenderResponse(1L, "Male");

        when(genderService.createGender(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/genders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Male"));
    }

    @Test
    void createGender_Returns400_WhenNameBlank() throws Exception {
        CreateGenderRequest request = new CreateGenderRequest("");

        mockMvc.perform(post("/api/v1/genders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createGender_Returns409_WhenDuplicate() throws Exception {
        CreateGenderRequest request = new CreateGenderRequest("Male");

        when(genderService.createGender(any()))
                .thenThrow(new DuplicateResourceException("Gender already exists."));

        mockMvc.perform(post("/api/v1/genders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Gender already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateGender_Returns200() throws Exception {
        UpdateGenderRequest request = new UpdateGenderRequest("Female");
        GenderResponse response = new GenderResponse(1L, "Female");

        when(genderService.updateGender(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/genders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Female"));
    }

    @Test
    void updateGender_Returns404_WhenNotFound() throws Exception {
        UpdateGenderRequest request = new UpdateGenderRequest("Female");

        when(genderService.updateGender(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Gender not found."));

        mockMvc.perform(put("/api/v1/genders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Gender not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getGender_Returns200() throws Exception {
        GenderResponse response = new GenderResponse(1L, "Male");

        when(genderService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/genders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Male"));
    }

    @Test
    void getGender_Returns404_WhenNotFound() throws Exception {
        when(genderService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Gender not found."));

        mockMvc.perform(get("/api/v1/genders/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Gender not found."));
    }

    @Test
    void getAllGenders_Returns200() throws Exception {
        List<GenderResponse> response = List.of(
                new GenderResponse(1L, "Male"),
                new GenderResponse(2L, "Female")
        );

        when(genderService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/genders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Male"))
                .andExpect(jsonPath("$[1].name").value("Female"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteGender_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/genders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteGender_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Gender not found."))
                .when(genderService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/genders/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Gender not found."));
    }
}