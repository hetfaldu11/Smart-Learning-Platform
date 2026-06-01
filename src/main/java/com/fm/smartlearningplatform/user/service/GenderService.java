package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.response.DeleteGenderResponse;
import com.fm.smartlearningplatform.user.dto.gender.response.GenderResponse;
import com.fm.smartlearningplatform.user.mapper.GenderMapper;
import com.fm.smartlearningplatform.user.model.Gender;
import com.fm.smartlearningplatform.user.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;
    private final GenderMapper genderMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public GenderResponse create(CreateGenderRequest request) {
        validateGenderNotExist(request.name());
        return genderMapper.toResponse(genderRepository.save(genderMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public GenderResponse update(Long id, UpdateGenderRequest request) {

        Gender gender = getGender(id);

        if (genderRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Gender already exist.");

        genderMapper.updateGenderFromRequest(request, gender);
        return genderMapper.toResponse(genderRepository.save(gender));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return genderRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public GenderResponse findById(Long id) {
        return genderMapper.toResponse(getGender(id));
    }

    @Transactional(readOnly = true)
    public List<GenderResponse> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        keyword = keyword.trim();
        return genderRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword)
                .stream()
                .map(genderMapper::toResponse)
                .toList();
    }

    private List<GenderResponse> findAll() {
        return genderRepository.findByDeletedAtIsNullOrderByNameAsc()
                .stream()
                .map(genderMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteGenderResponse deleteById(Long id) {
        Gender gender = getGender(id);

        gender.setDeletedAt(LocalDateTime.now());

        genderRepository.save(gender);

        return new DeleteGenderResponse("Gender is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateGenderNotExist(String name) {
        if (genderRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Gender already exist.");
    }

    private Gender getGender(Long id) {
        return genderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender not found."));
    }
}