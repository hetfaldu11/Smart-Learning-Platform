package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.ThemeController;
import com.fm.smartlearningplatform.dto.user.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.ThemeService;
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

@WebMvcTest(ThemeController.class)
class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ThemeService themeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createTheme_Returns201() throws Exception {
        CreateThemeRequest request = new CreateThemeRequest("Dark");
        ThemeResponse response = new ThemeResponse(1L, "Dark");

        when(themeService.createTheme(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Dark"));
    }

    @Test
    void createTheme_Returns400_WhenNameBlank() throws Exception {
        CreateThemeRequest request = new CreateThemeRequest("");

        mockMvc.perform(post("/api/v1/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTheme_Returns409_WhenDuplicate() throws Exception {
        CreateThemeRequest request = new CreateThemeRequest("Dark");

        when(themeService.createTheme(any()))
                .thenThrow(new DuplicateResourceException("Theme already exists."));

        mockMvc.perform(post("/api/v1/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Theme already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateTheme_Returns200() throws Exception {
        UpdateThemeRequest request = new UpdateThemeRequest("Light");
        ThemeResponse response = new ThemeResponse(1L, "Light");

        when(themeService.updateTheme(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Light"));
    }

    @Test
    void updateTheme_Returns404_WhenNotFound() throws Exception {
        UpdateThemeRequest request = new UpdateThemeRequest("Light");

        when(themeService.updateTheme(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Theme not found."));

        mockMvc.perform(put("/api/v1/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Theme not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getTheme_Returns200() throws Exception {
        ThemeResponse response = new ThemeResponse(1L, "Dark");

        when(themeService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/themes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Dark"));
    }

    @Test
    void getTheme_Returns404_WhenNotFound() throws Exception {
        when(themeService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Theme not found."));

        mockMvc.perform(get("/api/v1/themes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Theme not found."));
    }

    @Test
    void getAllThemes_Returns200() throws Exception {
        List<ThemeResponse> response = List.of(
                new ThemeResponse(1L, "Dark"),
                new ThemeResponse(2L, "Light")
        );

        when(themeService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/themes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dark"))
                .andExpect(jsonPath("$[1].name").value("Light"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteTheme_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/themes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTheme_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Theme not found."))
                .when(themeService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/themes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Theme not found."));
    }
}