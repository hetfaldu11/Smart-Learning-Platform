package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.mapper.ThemeMapper;
import com.fm.smartlearningplatform.user.model.Theme;
import com.fm.smartlearningplatform.user.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    private final ThemeMapper themeMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public ThemeResponse createTheme(CreateThemeRequest request) {
        if (themeRepository.existsByNameAndDeletedAtIsNull(request.name()))
            throw new DuplicateResourceException("Theme already exists.");

        return themeMapper.toResponse(
                themeRepository.save(
                        themeMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public ThemeResponse updateTheme(Long id, UpdateThemeRequest request) {
        Theme theme = themeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found."));

        if (themeRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Theme already exists.");

        themeMapper.updateThemeFromRequest(request, theme);

        return themeMapper.toResponse(themeRepository.save(theme));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return themeRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public ThemeResponse findByIdAndDeletedAtIsNull(Long id) {
        return themeMapper.toResponse(
                themeRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Theme not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return themeRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public ThemeResponse findByNameAndDeletedAtIsNull(String name) {
        return themeMapper.toResponse(
                themeRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Theme not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<ThemeResponse> findAllActive() {
        return themeRepository.findByDeletedAtIsNull()
                .stream()
                .map(themeMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id) {
        Theme theme = themeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not exist."));

        theme.setDeletedAt(LocalDateTime.now());

        themeRepository.save(theme);
    }
}
