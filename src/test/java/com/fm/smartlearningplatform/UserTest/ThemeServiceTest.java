package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.ThemeMapper;
import com.fm.smartlearningplatform.model.user.Theme;
import com.fm.smartlearningplatform.repository.user.ThemeRepository;
import com.fm.smartlearningplatform.service.user.ThemeService;
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
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private ThemeMapper themeMapper;

    @InjectMocks
    private ThemeService themeService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createTheme_Success() {
        CreateThemeRequest request = new CreateThemeRequest("Dark");
        Theme theme = Theme.builder().id(1L).name("Dark").build();
        ThemeResponse response = new ThemeResponse(1L, "Dark");

        when(themeRepository.existsByNameAndDeletedAtIsNull("Dark")).thenReturn(false);
        when(themeMapper.toEntity(request)).thenReturn(theme);
        when(themeRepository.save(theme)).thenReturn(theme);
        when(themeMapper.toResponse(theme)).thenReturn(response);

        ThemeResponse result = themeService.createTheme(request);

        assertThat(result.getName()).isEqualTo("Dark");
    }

    @Test
    void createTheme_ThrowsDuplicate() {
        CreateThemeRequest request = new CreateThemeRequest("Dark");

        when(themeRepository.existsByNameAndDeletedAtIsNull("Dark")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> themeService.createTheme(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateTheme_Success() {
        UpdateThemeRequest request = new UpdateThemeRequest("Light");
        Theme theme = Theme.builder().id(1L).name("Dark").build();
        ThemeResponse response = new ThemeResponse(1L, "Light");

        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(theme));
        when(themeRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Light")).thenReturn(false);
        when(themeRepository.save(theme)).thenReturn(theme);
        when(themeMapper.toResponse(theme)).thenReturn(response);

        ThemeResponse result = themeService.updateTheme(1L, request);

        assertThat(result.getName()).isEqualTo("Light");
    }

    @Test
    void updateTheme_ThrowsNotFound() {
        UpdateThemeRequest request = new UpdateThemeRequest("Light");

        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> themeService.updateTheme(1L, request));
    }

    @Test
    void updateTheme_ThrowsDuplicate() {
        UpdateThemeRequest request = new UpdateThemeRequest("Light");
        Theme theme = Theme.builder().id(1L).name("Dark").build();

        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(theme));
        when(themeRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Light")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> themeService.updateTheme(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Theme theme = Theme.builder().id(1L).name("Dark").build();
        ThemeResponse response = new ThemeResponse(1L, "Dark");

        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(theme));
        when(themeMapper.toResponse(theme)).thenReturn(response);

        ThemeResponse result = themeService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Dark");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> themeService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Theme theme = Theme.builder().id(1L).name("Dark").build();
        ThemeResponse response = new ThemeResponse(1L, "Dark");

        when(themeRepository.findByDeletedAtIsNull()).thenReturn(List.of(theme));
        when(themeMapper.toResponse(theme)).thenReturn(response);

        List<ThemeResponse> result = themeService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Dark");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Theme theme = Theme.builder().id(1L).name("Dark").build();

        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(theme));

        themeService.deleteById(1L);

        assertThat(theme.getDeletedAt()).isNotNull();
        verify(themeRepository).save(theme);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(themeRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> themeService.deleteById(1L));
    }
}