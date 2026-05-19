package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.ProfessionMapper;
import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.repository.user.ProfessionRepository;
import com.fm.smartlearningplatform.service.user.ProfessionService;
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
public class ProfessionServiceTest {

    @Mock
    private ProfessionRepository professionRepository;

    @Mock
    private ProfessionMapper professionMapper;

    @InjectMocks
    private ProfessionService professionService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createProfession_Success() {
        CreateProfessionRequest request = new CreateProfessionRequest("Student");
        Profession profession = Profession.builder().id(1L).name("Student").build();
        ProfessionResponse response = new ProfessionResponse(1L, "Student");

        when(professionRepository.existsByNameAndDeletedAtIsNull("Student")).thenReturn(false);
        when(professionMapper.toEntity(request)).thenReturn(profession);
        when(professionRepository.save(profession)).thenReturn(profession);
        when(professionMapper.toResponse(profession)).thenReturn(response);

        ProfessionResponse result = professionService.createProfession(request);

        assertThat(result.getName()).isEqualTo("Student");
    }

    @Test
    void createProfession_ThrowsDuplicate() {
        CreateProfessionRequest request = new CreateProfessionRequest("Student");

        when(professionRepository.existsByNameAndDeletedAtIsNull("Student")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> professionService.createProfession(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateProfession_Success() {
        UpdateProfessionRequest request = new UpdateProfessionRequest("Web developer");
        Profession profession = Profession.builder().id(1L).name("Student").build();
        ProfessionResponse response = new ProfessionResponse(1L, "Web developer");

        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(profession));
        when(professionRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Web developer")).thenReturn(false);
        when(professionRepository.save(profession)).thenReturn(profession);
        when(professionMapper.toResponse(profession)).thenReturn(response);

        ProfessionResponse result = professionService.updateProfession(1L, request);

        assertThat(result.getName()).isEqualTo("Web developer");
    }

    @Test
    void updateProfession_ThrowsNotFound() {
        UpdateProfessionRequest request = new UpdateProfessionRequest("Web developer");

        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> professionService.updateProfession(1L, request));
    }

    @Test
    void updateProfession_ThrowsDuplicate() {
        UpdateProfessionRequest request = new UpdateProfessionRequest("Web developer");
        Profession profession = Profession.builder().id(1L).name("Student").build();

        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(profession));
        when(professionRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Web developer")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> professionService.updateProfession(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Profession profession = Profession.builder().id(1L).name("Student").build();
        ProfessionResponse response = new ProfessionResponse(1L, "Student");

        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(profession));
        when(professionMapper.toResponse(profession)).thenReturn(response);

        ProfessionResponse result = professionService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Student");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> professionService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Profession profession = Profession.builder().id(1L).name("Student").build();
        ProfessionResponse response = new ProfessionResponse(1L, "Student");

        when(professionRepository.findByDeletedAtIsNull()).thenReturn(List.of(profession));
        when(professionMapper.toResponse(profession)).thenReturn(response);

        List<ProfessionResponse> result = professionService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Student");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Profession profession = Profession.builder().id(1L).name("Student").build();

        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(profession));

        professionService.deleteById(1L);

        assertThat(profession.getDeletedAt()).isNotNull();
        verify(professionRepository).save(profession);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(professionRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> professionService.deleteById(1L));
    }
}