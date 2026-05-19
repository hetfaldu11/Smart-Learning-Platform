package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.response.LanguageResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.LanguageMapper;
import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.repository.user.LanguageRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    private final LanguageMapper languageMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public LanguageResponse createLanguage(CreateLanguageRequest request) {
        if (languageRepository.existsByNameAndDeletedAtIsNull(request.getName()))
            throw new DuplicateResourceException("Language name already exists.");

        if (languageRepository.existsByCodeAndDeletedAtIsNull(request.getCode()))
            throw new DuplicateResourceException("Language code already exists.");

        return languageMapper.toResponse(
                languageRepository.save(
                        languageMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public LanguageResponse updateLanguage(Long id, UpdateLanguageRequest request) {
        Language language = languageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found."));

        if (request.getName()!=null && languageRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.getName()))
            throw new DuplicateResourceException("Language already exists.");

        if (request.getCode()!=null && languageRepository.existsByIdNotAndCodeAndDeletedAtIsNull(id, request.getCode()))
            throw new DuplicateResourceException("Code already exists.");

        languageMapper.updateLanguageFromRequest(request, language);

        return languageMapper.toResponse(languageRepository.save(language));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return languageRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public LanguageResponse findByIdAndDeletedAtIsNull(Long id){
        return languageMapper.toResponse(
                languageRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Language not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name){
        return languageRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public LanguageResponse findByNameAndDeletedAtIsNull(String name){
        return languageMapper.toResponse(
                languageRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(()->new ResourceNotFoundException("Language not exists."))
        );
    }

    @Transactional(readOnly = true)
    public boolean existsByCodeAndDeletedAtIsNull(String code){
        return languageRepository.existsByCodeAndDeletedAtIsNull(code);
    }

    @Transactional(readOnly = true)
    public LanguageResponse findByCodeAndDeletedAtIsNull(String code){
        return languageMapper.toResponse(
                languageRepository.findByCodeAndDeletedAtIsNull(code)
                        .orElseThrow(()->new ResourceNotFoundException("Language not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<LanguageResponse> findAllActive() {
        return languageRepository.findByDeletedAtIsNull()
                .stream()
                .map(languageMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        Language language = languageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not exist."));

        language.setDeletedAt(LocalDateTime.now());

        languageRepository.save(language);
    }
}
