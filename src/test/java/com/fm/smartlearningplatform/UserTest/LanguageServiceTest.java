package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.response.LanguageResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.LanguageMapper;
import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.repository.user.LanguageRepository;
import com.fm.smartlearningplatform.service.user.LanguageService;
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
class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private LanguageMapper languageMapper;

    @InjectMocks
    private LanguageService languageService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createLanguage_Success() {
        CreateLanguageRequest request = new CreateLanguageRequest("Hindi","HN");
        Language language = Language.builder().id(1L).name("Hindi").code("HN").build();
        LanguageResponse response = new LanguageResponse(1L, "Hindi", "HN");

        when(languageRepository.existsByNameAndDeletedAtIsNull("Hindi")).thenReturn(false);
        when(languageRepository.existsByCodeAndDeletedAtIsNull("HN")).thenReturn(false);
        when(languageMapper.toEntity(request)).thenReturn(language);
        when(languageRepository.save(language)).thenReturn(language);
        when(languageMapper.toResponse(language)).thenReturn(response);

        LanguageResponse result = languageService.createLanguage(request);

        assertThat(result.getName()).isEqualTo("Hindi");
        assertThat(result.getCode()).isEqualTo("HN");
    }

    @Test
    void createLanguage_ThrowsDuplicate() {
        CreateLanguageRequest request = new CreateLanguageRequest("Hindi","HN");

        when(languageRepository.existsByNameAndDeletedAtIsNull("Hindi")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> languageService.createLanguage(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateLanguageName_Success() {
        UpdateLanguageRequest request = new UpdateLanguageRequest("English","EN");
        Language language = Language.builder().id(1L).name("Hindi").code("HN").build();
        LanguageResponse response = new LanguageResponse(1L, "English","EN");

        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(language));
        when(languageRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "English")).thenReturn(false);
        when(languageRepository.save(language)).thenReturn(language);
        when(languageMapper.toResponse(language)).thenReturn(response);

        LanguageResponse result = languageService.updateLanguage(1L, request);

        assertThat(result.getName()).isEqualTo("English");
    }

    @Test
    void updateLanguageName_ThrowsNotFound() {
        UpdateLanguageRequest request = new UpdateLanguageRequest("English","EN");

        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> languageService.updateLanguage(1L, request));
    }

    @Test
    void updateLanguageName_ThrowsDuplicate() {
        UpdateLanguageRequest request = new UpdateLanguageRequest("English","EN");
        Language language = Language.builder().id(1L).name("Hindi").code("HN").build();

        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(language));
        when(languageRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "English")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> languageService.updateLanguage(1L, request));
    }


    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Language language = Language.builder().id(1L).name("Hindi").code("HN").build();
        LanguageResponse response = new LanguageResponse(1L, "Hindi","HN");

        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(language));
        when(languageMapper.toResponse(language)).thenReturn(response);

        LanguageResponse result = languageService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Hindi");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> languageService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Language language = Language.builder().id(1L).name("Hindi").code("HN").build();
        LanguageResponse response = new LanguageResponse(1L, "Hindi","HN");

        when(languageRepository.findByDeletedAtIsNull()).thenReturn(List.of(language));
        when(languageMapper.toResponse(language)).thenReturn(response);

        List<LanguageResponse> result = languageService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Hindi");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Language language = Language.builder().id(1L).name("Hindi").code("HN").build();

        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(language));

        languageService.deleteById(1L);

        assertThat(language.getDeletedAt()).isNotNull();
        verify(languageRepository).save(language);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(languageRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> languageService.deleteById(1L));
    }
}