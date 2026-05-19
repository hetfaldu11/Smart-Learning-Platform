package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.PlatformController;
import com.fm.smartlearningplatform.dto.user.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.PlatformService;
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

@WebMvcTest(PlatformController.class)
class PlatformControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlatformService platformService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createPlatform_Returns201() throws Exception {
        CreatePlatformRequest request = new CreatePlatformRequest("Github");
        PlatformResponse response = new PlatformResponse(1L, "Github");

        when(platformService.createPlatform(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/platforms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Github"));
    }

    @Test
    void createPlatform_Returns400_WhenNameBlank() throws Exception {
        CreatePlatformRequest request = new CreatePlatformRequest("");

        mockMvc.perform(post("/api/v1/platforms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPlatform_Returns409_WhenDuplicate() throws Exception {
        CreatePlatformRequest request = new CreatePlatformRequest("Github");

        when(platformService.createPlatform(any()))
                .thenThrow(new DuplicateResourceException("Platform already exists."));

        mockMvc.perform(post("/api/v1/platforms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Platform already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updatePlatform_Returns200() throws Exception {
        UpdatePlatformRequest request = new UpdatePlatformRequest("Linkdin");
        PlatformResponse response = new PlatformResponse(1L, "Linkdin");

        when(platformService.updatePlatform(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/platforms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Linkdin"));
    }

    @Test
    void updatePlatform_Returns404_WhenNotFound() throws Exception {
        UpdatePlatformRequest request = new UpdatePlatformRequest("Linkdin");

        when(platformService.updatePlatform(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Platform not found."));

        mockMvc.perform(put("/api/v1/platforms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Platform not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getPlatform_Returns200() throws Exception {
        PlatformResponse response = new PlatformResponse(1L, "Github");

        when(platformService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/platforms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Github"));
    }

    @Test
    void getPlatform_Returns404_WhenNotFound() throws Exception {
        when(platformService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Platform not found."));

        mockMvc.perform(get("/api/v1/platforms/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Platform not found."));
    }

    @Test
    void getAllPlatforms_Returns200() throws Exception {
        List<PlatformResponse> response = List.of(
                new PlatformResponse(1L, "Github"),
                new PlatformResponse(2L, "Linkdin")
        );

        when(platformService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/platforms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Github"))
                .andExpect(jsonPath("$[1].name").value("Linkdin"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deletePlatform_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/platforms/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePlatform_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Platform not found."))
                .when(platformService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/platforms/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Platform not found."));
    }
}