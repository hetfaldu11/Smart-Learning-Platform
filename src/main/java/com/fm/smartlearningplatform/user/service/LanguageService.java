package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.response.DeleteLanguageResponse;
import com.fm.smartlearningplatform.user.dto.language.response.LanguageResponse;
import com.fm.smartlearningplatform.user.mapper.LanguageMapper;
import com.fm.smartlearningplatform.user.model.Language;
import com.fm.smartlearningplatform.user.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public LanguageResponse create(CreateLanguageRequest request) {
        validateLanguageNotExist(request.name());
        validateCodeNotExist(request.code());
        return languageMapper.toResponse(languageRepository.save(languageMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public LanguageResponse update(Long id, UpdateLanguageRequest request) {

        Language language = getLanguage(id);

        if (languageRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Language already exist.");

        if (languageRepository.existsByIdNotAndCodeAndDeletedAtIsNull(id, request.code()))
            throw new DuplicateResourceException("Code already exist.");

        languageMapper.updateLanguageFromRequest(request, language);
        return languageMapper.toResponse(languageRepository.save(language));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return languageRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public LanguageResponse findById(Long id) {
        return languageMapper.toResponse(getLanguage(id));
    }

    @Transactional(readOnly = true)
    public Page<LanguageResponse> searchByKeyword(
            String keyword,
            Pageable pageable
    ) {

        Page<Language> languages;

        if (keyword == null || keyword.isBlank()) {

            languages = languageRepository.findByDeletedAtIsNull(pageable);

        } else {

            keyword = keyword
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();

            languages = languageRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }

        return languages.map(languageMapper::toResponse);
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteLanguageResponse deleteById(Long id) {
        Language language = getLanguage(id);

        language.setDeletedAt(LocalDateTime.now());

        languageRepository.save(language);

        return new DeleteLanguageResponse("Language is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateLanguageNotExist(String name) {
        if (languageRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Language already exist.");
    }

    private void validateCodeNotExist(String code) {
        if (languageRepository.existsByCodeAndDeletedAtIsNull(code))
            throw new DuplicateResourceException("Code already exist.");
    }

    private Language getLanguage(Long id) {
        return languageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found."));
    }
}
