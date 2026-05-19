package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.response.GenderResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.GenderMapper;
import com.fm.smartlearningplatform.model.user.Gender;
import com.fm.smartlearningplatform.repository.user.GenderRepository;
import com.fm.smartlearningplatform.service.user.GenderService;
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
public class GenderServiceTest {

    @Mock
    private GenderRepository genderRepository;

    @Mock
    private GenderMapper genderMapper;

    @InjectMocks
    private GenderService genderService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createGender_Success() {
        CreateGenderRequest request = new CreateGenderRequest("Male");
        Gender gender = Gender.builder().id(1L).name("Male").build();
        GenderResponse response = new GenderResponse(1L, "Male");

        when(genderRepository.existsByNameAndDeletedAtIsNull("Male")).thenReturn(false);
        when(genderMapper.toEntity(request)).thenReturn(gender);
        when(genderRepository.save(gender)).thenReturn(gender);
        when(genderMapper.toResponse(gender)).thenReturn(response);

        GenderResponse result = genderService.createGender(request);

        assertThat(result.getName()).isEqualTo("Male");
    }

    @Test
    void createGender_ThrowsDuplicate() {
        CreateGenderRequest request = new CreateGenderRequest("Male");

        when(genderRepository.existsByNameAndDeletedAtIsNull("Male")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> genderService.createGender(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateGender_Success() {
        UpdateGenderRequest request = new UpdateGenderRequest("Female");
        Gender gender = Gender.builder().id(1L).name("Male").build();
        GenderResponse response = new GenderResponse(1L, "Female");

        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(gender));
        when(genderRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Female")).thenReturn(false);
        when(genderRepository.save(gender)).thenReturn(gender);
        when(genderMapper.toResponse(gender)).thenReturn(response);

        GenderResponse result = genderService.updateGender(1L, request);

        assertThat(result.getName()).isEqualTo("Female");
    }

    @Test
    void updateGender_ThrowsNotFound() {
        UpdateGenderRequest request = new UpdateGenderRequest("Female");

        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> genderService.updateGender(1L, request));
    }

    @Test
    void updateGender_ThrowsDuplicate() {
        UpdateGenderRequest request = new UpdateGenderRequest("Female");
        Gender gender = Gender.builder().id(1L).name("Male").build();

        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(gender));
        when(genderRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Female")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> genderService.updateGender(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Gender gender = Gender.builder().id(1L).name("Male").build();
        GenderResponse response = new GenderResponse(1L, "Male");

        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(gender));
        when(genderMapper.toResponse(gender)).thenReturn(response);

        GenderResponse result = genderService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Male");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> genderService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Gender gender = Gender.builder().id(1L).name("Male").build();
        GenderResponse response = new GenderResponse(1L, "Male");

        when(genderRepository.findByDeletedAtIsNull()).thenReturn(List.of(gender));
        when(genderMapper.toResponse(gender)).thenReturn(response);

        List<GenderResponse> result = genderService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Male");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Gender gender = Gender.builder().id(1L).name("Male").build();

        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(gender));

        genderService.deleteById(1L);

        assertThat(gender.getDeletedAt()).isNotNull();
        verify(genderRepository).save(gender);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(genderRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> genderService.deleteById(1L));
    }
}