package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.ProfessionMapper;
import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.repository.user.ProfessionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepository;

    private final ProfessionMapper professionMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public ProfessionResponse createProfession(CreateProfessionRequest request) {
        if (professionRepository.existsByNameAndDeletedAtIsNull(request.getName()))
            throw new DuplicateResourceException("Profession already exists.");

        return professionMapper.toResponse(
                professionRepository.save(
                        professionMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public ProfessionResponse updateProfession(Long id, UpdateProfessionRequest request) {
        Profession profession = professionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found."));

        if (professionRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.getName()))
            throw new DuplicateResourceException("Profession already exists.");

        professionMapper.updateProfessionFromRequest(request, profession);

        return professionMapper.toResponse(professionRepository.save(profession));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return professionRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public ProfessionResponse findByIdAndDeletedAtIsNull(Long id){
        return professionMapper.toResponse(
                professionRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Profession not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name){
        return professionRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public ProfessionResponse findByNameAndDeletedAtIsNull(String name){
        return professionMapper.toResponse(
                professionRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(()->new ResourceNotFoundException("Profession not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<ProfessionResponse> findAllActive() {
        return professionRepository.findByDeletedAtIsNull()
                .stream()
                .map(professionMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        Profession profession = professionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not exist."));

        profession.setDeletedAt(LocalDateTime.now());

        professionRepository.save(profession);
    }
}
