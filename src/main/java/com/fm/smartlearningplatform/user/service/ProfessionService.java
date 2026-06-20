package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.response.DeleteProfessionResponse;
import com.fm.smartlearningplatform.user.dto.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.user.mapper.ProfessionMapper;
import com.fm.smartlearningplatform.user.model.Profession;
import com.fm.smartlearningplatform.user.repository.ProfessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepository;
    private final ProfessionMapper professionMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public ProfessionResponse create(CreateProfessionRequest request) {
        validateProfessionNotExist(request.name());
        return professionMapper.toResponse(professionRepository.save(professionMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public ProfessionResponse update(Long id, UpdateProfessionRequest request) {

        Profession profession = getProfession(id);

        if (professionRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Profession already exist.");

        professionMapper.updateProfessionFromRequest(request, profession);
        return professionMapper.toResponse(professionRepository.save(profession));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return professionRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public ProfessionResponse findById(Long id) {
        return professionMapper.toResponse(getProfession(id));
    }

    @Transactional(readOnly = true)
    public Page<ProfessionResponse> searchByKeyword(String keyword, Pageable pageable) {
        Page<Profession> professions;
        if (keyword == null || keyword.isBlank()) {
            professions = professionRepository.findByDeletedAtIsNull(pageable);
        } else {
            keyword = keyword.trim();
            professions = professionRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }
        return professions.map(professionMapper::toResponse);
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteProfessionResponse deleteById(Long id) {
        Profession profession = getProfession(id);

        profession.setDeletedAt(LocalDateTime.now());

        professionRepository.save(profession);

        return new DeleteProfessionResponse("Profession is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateProfessionNotExist(String name) {
        if (professionRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Profession already exist.");
    }

    private Profession getProfession(Long id) {
        return professionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found."));
    }
}
