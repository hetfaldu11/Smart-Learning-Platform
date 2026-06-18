package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.dto.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.response.DeleteThemeResponse;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.mapper.ThemeMapper;
import com.fm.smartlearningplatform.user.model.Theme;
import com.fm.smartlearningplatform.user.model.Theme;
import com.fm.smartlearningplatform.user.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ThemeResponse create(CreateThemeRequest request) {
        validateThemeNotExist(request.name());
        return themeMapper.toResponse(themeRepository.save(themeMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public ThemeResponse update(Long id, UpdateThemeRequest request) {

        Theme theme = getTheme(id);

        if (themeRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Theme already exist.");

        themeMapper.updateThemeFromRequest(request, theme);
        return themeMapper.toResponse(themeRepository.save(theme));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return themeRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public ThemeResponse findById(Long id) {
        return themeMapper.toResponse(getTheme(id));
    }

    @Transactional(readOnly = true)
    public Page<ThemeResponse> searchByKeyword(String keyword, Pageable pageable)
    {
        Page<Theme> themes;
        if (keyword == null || keyword.isBlank()) {
            themes = themeRepository.findByDeletedAtIsNull(pageable);
        } else {
            keyword = keyword.trim();
            themes = themeRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }
        return themes.map(themeMapper::toResponse);
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteThemeResponse deleteById(Long id) {
        Theme theme = getTheme(id);

        theme.setDeletedAt(LocalDateTime.now());

        themeRepository.save(theme);

        return new DeleteThemeResponse("Theme is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateThemeNotExist(String name) {
        if (themeRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Theme already exist.");
    }

    private Theme getTheme(Long id) {
        return themeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found."));
    }
}
