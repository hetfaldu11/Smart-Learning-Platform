package com.fm.smartlearningplatform.UserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.smartlearningplatform.controller.user.RoleController;
import com.fm.smartlearningplatform.dto.user.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.response.RoleResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.service.user.RoleService;
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

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoleService roleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createRole_Returns201() throws Exception {
        CreateRoleRequest request = new CreateRoleRequest("Java");
        RoleResponse response = new RoleResponse(1L, "Java");

        when(roleService.createRole(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void createRole_Returns400_WhenNameBlank() throws Exception {
        CreateRoleRequest request = new CreateRoleRequest("");

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRole_Returns409_WhenDuplicate() throws Exception {
        CreateRoleRequest request = new CreateRoleRequest("Java");

        when(roleService.createRole(any()))
                .thenThrow(new DuplicateResourceException("Role already exists."));

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Role already exists."));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateRole_Returns200() throws Exception {
        UpdateRoleRequest request = new UpdateRoleRequest("Python");
        RoleResponse response = new RoleResponse(1L, "Python");

        when(roleService.updateRole(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Python"));
    }

    @Test
    void updateRole_Returns404_WhenNotFound() throws Exception {
        UpdateRoleRequest request = new UpdateRoleRequest("Python");

        when(roleService.updateRole(eq(1L), any()))
                .thenThrow(new ResourceNotFoundException("Role not found."));

        mockMvc.perform(put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Role not found."));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void getRole_Returns200() throws Exception {
        RoleResponse response = new RoleResponse(1L, "Java");

        when(roleService.findByIdAndDeletedAtIsNull(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getRole_Returns404_WhenNotFound() throws Exception {
        when(roleService.findByIdAndDeletedAtIsNull(1L))
                .thenThrow(new ResourceNotFoundException("Role not found."));

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Role not found."));
    }

    @Test
    void getAllRoles_Returns200() throws Exception {
        List<RoleResponse> response = List.of(
                new RoleResponse(1L, "Java"),
                new RoleResponse(2L, "Python")
        );

        when(roleService.findAllActive()).thenReturn(response);

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[1].name").value("Python"));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteRole_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/roles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRole_Returns404_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Role not found."))
                .when(roleService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/roles/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Role not found."));
    }
}