package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.response.RoleResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.RoleMapper;
import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import com.fm.smartlearningplatform.service.user.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createRole_Success() {
        CreateRoleRequest request = new CreateRoleRequest("Java");
        Role role = Role.builder().id(1L).name("Java").build();
        RoleResponse response = new RoleResponse(1L, "Java");

        when(roleRepository.existsByNameAndDeletedAtIsNull("Java")).thenReturn(false);
        when(roleMapper.toEntity(request)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toResponse(role)).thenReturn(response);

        RoleResponse result = roleService.createRole(request);

        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void createRole_ThrowsDuplicate() {
        CreateRoleRequest request = new CreateRoleRequest("Java");

        when(roleRepository.existsByNameAndDeletedAtIsNull("Java")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> roleService.createRole(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateRole_Success() {
        UpdateRoleRequest request = new UpdateRoleRequest("Python");
        Role role = Role.builder().id(1L).name("Java").build();
        RoleResponse response = new RoleResponse(1L, "Python");

        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(role));
        when(roleRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Python")).thenReturn(false);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toResponse(role)).thenReturn(response);

        RoleResponse result = roleService.updateRole(1L, request);

        assertThat(result.getName()).isEqualTo("Python");
    }

    @Test
    void updateRole_ThrowsNotFound() {
        UpdateRoleRequest request = new UpdateRoleRequest("Python");

        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> roleService.updateRole(1L, request));
    }

    @Test
    void updateRole_ThrowsDuplicate() {
        UpdateRoleRequest request = new UpdateRoleRequest("Python");
        Role role = Role.builder().id(1L).name("Java").build();

        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(role));
        when(roleRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Python")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> roleService.updateRole(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Role role = Role.builder().id(1L).name("Java").build();
        RoleResponse response = new RoleResponse(1L, "Java");

        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toResponse(role)).thenReturn(response);

        RoleResponse result = roleService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> roleService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Role role = Role.builder().id(1L).name("Java").build();
        RoleResponse response = new RoleResponse(1L, "Java");

        when(roleRepository.findByDeletedAtIsNull()).thenReturn(List.of(role));
        when(roleMapper.toResponse(role)).thenReturn(response);

        List<RoleResponse> result = roleService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Java");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Role role = Role.builder().id(1L).name("Java").build();

        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(role));

        roleService.deleteById(1L);

        assertThat(role.getDeletedAt()).isNotNull();
        verify(roleRepository).save(role);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(roleRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> roleService.deleteById(1L));
    }
}