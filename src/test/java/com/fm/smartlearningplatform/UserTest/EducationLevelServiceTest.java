package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.EducationLevelMapper;
import com.fm.smartlearningplatform.model.user.EducationLevel;
import com.fm.smartlearningplatform.repository.user.EducationLevelRepository;
import com.fm.smartlearningplatform.service.user.EducationLevelService;
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
class EducationLevelServiceTest {

    @Mock
    private EducationLevelRepository educationLevelRepository;


    @Mock
    private EducationLevelMapper educationLevelMapper;

    @InjectMocks
    private EducationLevelService educationLevelService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createEducationLevel_Success() {
        CreateEducationLevelRequest request = new CreateEducationLevelRequest("School");
        EducationLevel educationLevel = EducationLevel.builder().id(1L).name("School").build();
        EducationLevelResponse response = new EducationLevelResponse(1L, "School");

        when(educationLevelRepository.existsByNameAndDeletedAtIsNull("School")).thenReturn(false);
        when(educationLevelMapper.toEntity(request)).thenReturn(educationLevel);
        when(educationLevelRepository.save(educationLevel)).thenReturn(educationLevel);
        when(educationLevelMapper.toResponse(educationLevel)).thenReturn(response);

        EducationLevelResponse result = educationLevelService.createEducationLevel(request);

        assertThat(result.getName()).isEqualTo("School");
    }

    @Test
    void createEducationLevel_ThrowsDuplicate() {
        CreateEducationLevelRequest request = new CreateEducationLevelRequest("School");

        when(educationLevelRepository.existsByNameAndDeletedAtIsNull("School")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> educationLevelService.createEducationLevel(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateEducationLevel_Success() {
        UpdateEducationLevelRequest request = new UpdateEducationLevelRequest("Diploma");
        EducationLevel educationLevel = EducationLevel.builder().id(1L).name("School").build();
        EducationLevelResponse response = new EducationLevelResponse(1L, "Diploma");

        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(educationLevel));
        when(educationLevelRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Diploma")).thenReturn(false);
        when(educationLevelRepository.save(educationLevel)).thenReturn(educationLevel);
        when(educationLevelMapper.toResponse(educationLevel)).thenReturn(response);

        EducationLevelResponse result = educationLevelService.updateEducationLevel(1L, request);

        assertThat(result.getName()).isEqualTo("Diploma");
    }

    @Test
    void updateEducationLevel_ThrowsNotFound() {
        UpdateEducationLevelRequest request = new UpdateEducationLevelRequest("Diploma");

        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> educationLevelService.updateEducationLevel(1L, request));
    }

    @Test
    void updateEducationLevel_ThrowsDuplicate() {
        UpdateEducationLevelRequest request = new UpdateEducationLevelRequest("Diploma");
        EducationLevel educationLevel = EducationLevel.builder().id(1L).name("School").build();

        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(educationLevel));
        when(educationLevelRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Diploma")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> educationLevelService.updateEducationLevel(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        EducationLevel educationLevel = EducationLevel.builder().id(1L).name("School").build();
        EducationLevelResponse response = new EducationLevelResponse(1L, "School");

        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(educationLevel));
        when(educationLevelMapper.toResponse(educationLevel)).thenReturn(response);

        EducationLevelResponse result = educationLevelService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("School");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> educationLevelService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        EducationLevel educationLevel = EducationLevel.builder().id(1L).name("School").build();
        EducationLevelResponse response = new EducationLevelResponse(1L, "School");

        when(educationLevelRepository.findByDeletedAtIsNull()).thenReturn(List.of(educationLevel));
        when(educationLevelMapper.toResponse(educationLevel)).thenReturn(response);

        List<EducationLevelResponse> result = educationLevelService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("School");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        EducationLevel educationLevel = EducationLevel.builder().id(1L).name("School").build();

        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(educationLevel));

        educationLevelService.deleteById(1L);

        assertThat(educationLevel.getDeletedAt()).isNotNull();
        verify(educationLevelRepository).save(educationLevel);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(educationLevelRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> educationLevelService.deleteById(1L));
    }
}