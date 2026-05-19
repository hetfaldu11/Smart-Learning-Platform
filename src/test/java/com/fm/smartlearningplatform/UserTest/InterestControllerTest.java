package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.InterestController;
import com.fm.smartlearningplatform.dto.user.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.response.InterestResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.InterestService;
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

@WebMvcTest(InterestController.class)
class InterestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InterestService interestService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createInterest_Returns201() throws Exception {
        CreateInterestRequest request = new CreateInterestRequest("Java");
        InterestResponse response = new InterestResponse(1L, "Java");

        when(interestService.createInterest(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/interests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void createInterest_Returns400_WhenNameBlank() throws Exception {
        CreateInterestRequest request = new CreateInterestRequest("");

        mockMvc.perform(post("/api/v1/interests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createInterest_Returns409_WhenDuplicate() throws Exception {
        CreateInterestRequest request = new CreateInterestRequest("Java");

        when(interestService.createInterest(any()))
                .thenThrow(new DuplicateResourceException("Interest already exists."));

        mockMvc.perform(post("/api/v1/interests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Interest already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateInterest_Returns200() throws Exception {
        UpdateInterestRequest request = new UpdateInterestRequest("Python");
        InterestResponse response = new InterestResponse(1L, "Python");

        when(interestService.updateInterest(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/interests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Python"));
    }

    @Test
    void updateInterest_Returns404_WhenNotFound() throws Exception {
        UpdateInterestRequest request = new UpdateInterestRequest("Python");

        when(interestService.updateInterest(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Interest not found."));

        mockMvc.perform(put("/api/v1/interests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Interest not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getInterest_Returns200() throws Exception {
        InterestResponse response = new InterestResponse(1L, "Java");

        when(interestService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/interests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getInterest_Returns404_WhenNotFound() throws Exception {
        when(interestService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Interest not found."));

        mockMvc.perform(get("/api/v1/interests/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Interest not found."));
    }

    @Test
    void getAllInterests_Returns200() throws Exception {
        List<InterestResponse> response = List.of(
                new InterestResponse(1L, "Java"),
                new InterestResponse(2L, "Python")
        );

        when(interestService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/interests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[1].name").value("Python"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteInterest_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/interests/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteInterest_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Interest not found."))
                .when(interestService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/interests/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Interest not found."));
    }
}