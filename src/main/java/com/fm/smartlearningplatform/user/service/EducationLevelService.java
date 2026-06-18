package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.DeleteEducationLevelResponse;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.user.mapper.EducationLevelMapper;
import com.fm.smartlearningplatform.user.model.EducationLevel;
import com.fm.smartlearningplatform.user.model.EducationLevel;
import com.fm.smartlearningplatform.user.repository.EducationLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationLevelService {

    private final EducationLevelRepository educationLevelRepository;
    private final EducationLevelMapper educationLevelMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public EducationLevelResponse create(CreateEducationLevelRequest request) {
        validateEducationLevelNotExist(request.name());
        return educationLevelMapper.toResponse(educationLevelRepository.save(educationLevelMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public EducationLevelResponse update(Long id, UpdateEducationLevelRequest request) {

        EducationLevel educationLevel = getEducationLevel(id);

        if (educationLevelRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("EducationLevel already exist.");

        educationLevelMapper.updateEducationLevelFromRequest(request, educationLevel);
        return educationLevelMapper.toResponse(educationLevelRepository.save(educationLevel));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return educationLevelRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public EducationLevelResponse findById(Long id) {
        return educationLevelMapper.toResponse(getEducationLevel(id));
    }

    @Transactional(readOnly = true)
    public Page<EducationLevelResponse> searchByKeyword(String keyword, Pageable pageable)
    {
        Page<EducationLevel> educationLevels;
        if (keyword == null || keyword.isBlank()) {
            educationLevels = educationLevelRepository.findByDeletedAtIsNull(pageable);
        } else {
            keyword = keyword.trim();
            educationLevels = educationLevelRepository .findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }
        return educationLevels.map(educationLevelMapper::toResponse);
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteEducationLevelResponse deleteById(Long id) {
        EducationLevel educationLevel = getEducationLevel(id);

        educationLevel.setDeletedAt(LocalDateTime.now());

        educationLevelRepository.save(educationLevel);

        return new DeleteEducationLevelResponse("EducationLevel is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateEducationLevelNotExist(String name) {
        if (educationLevelRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("EducationLevel already exist.");
    }

    private EducationLevel getEducationLevel(Long id) {
        return educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("EducationLevel not found."));
    }
}
