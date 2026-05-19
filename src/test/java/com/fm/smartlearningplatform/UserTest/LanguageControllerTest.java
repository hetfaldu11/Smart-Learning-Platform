package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.LanguageController;
import com.fm.smartlearningplatform.dto.user.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.response.LanguageResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.LanguageService;
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

@WebMvcTest(LanguageController.class)
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LanguageService languageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createLanguage_Returns201() throws Exception {
        CreateLanguageRequest request = new CreateLanguageRequest("Hindi","HN");
        LanguageResponse response = new LanguageResponse(1L, "Hindi","HN");

        when(languageService.createLanguage(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Hindi"))
                .andExpect(jsonPath("$.code").value("HN"));
    }

    @Test
    void createLanguage_Returns400_WhenNameBlank() throws Exception {
        CreateLanguageRequest request = new CreateLanguageRequest(null, "HN");

        mockMvc.perform(post("/api/v1/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createLanguage_Returns409_WhenDuplicate() throws Exception {
        CreateLanguageRequest request = new CreateLanguageRequest("Hindi","HN");

        when(languageService.createLanguage(any()))
                .thenThrow(new DuplicateResourceException("Language already exists."));

        mockMvc.perform(post("/api/v1/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Language already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateLanguage_Returns200() throws Exception {
        UpdateLanguageRequest request = new UpdateLanguageRequest("English","EN");
        LanguageResponse response = new LanguageResponse(1L, "English","EN");

        when(languageService.updateLanguage(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/languages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("English"))
                .andExpect(jsonPath("$.code").value("EN"));

    }

    @Test
    void updateLanguage_Returns404_WhenNotFound() throws Exception {
        UpdateLanguageRequest request = new UpdateLanguageRequest("English","EN");

        when(languageService.updateLanguage(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Language not found."));

        mockMvc.perform(put("/api/v1/languages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Language not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getLanguage_Returns200() throws Exception {
        LanguageResponse response = new LanguageResponse(1L, "Hindi","HN");

        when(languageService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/languages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Hindi"))
                .andExpect(jsonPath("$.code").value("HN"));
    }

    @Test
    void getLanguage_Returns404_WhenNotFound() throws Exception {
        when(languageService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Language not found."));

        mockMvc.perform(get("/api/v1/languages/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Language not found."));
    }

    @Test
    void getAllLanguages_Returns200() throws Exception {
        List<LanguageResponse> response = List.of(
                new LanguageResponse(1L, "Hindi","HN"),
                new LanguageResponse(2L, "English","EN")
        );

        when(languageService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Hindi"))
                .andExpect(jsonPath("$[1].name").value("English"))
                .andExpect(jsonPath("$[0].code").value("HN"))
                .andExpect(jsonPath("$[1].code").value("EN"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteLanguage_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/languages/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteLanguage_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Language not found."))
                .when(languageService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/languages/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Language not found."));
    }
}