package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.response.GenderResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.GenderMapper;
import com.fm.smartlearningplatform.model.user.Gender;
import com.fm.smartlearningplatform.repository.user.GenderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;

    private final GenderMapper genderMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public GenderResponse createGender(CreateGenderRequest request) {
        if (genderRepository.existsByNameAndDeletedAtIsNull(request.getName()))
            throw new DuplicateResourceException("Gender already exists.");

        return genderMapper.toResponse(
                genderRepository.save(
                        genderMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public GenderResponse updateGender(Long id, UpdateGenderRequest request) {
        Gender gender = genderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender not found."));

        if (genderRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.getName()))
            throw new DuplicateResourceException("Gender already exists.");

        genderMapper.updateGenderFromRequest(request, gender);

        return genderMapper.toResponse(genderRepository.save(gender));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return genderRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public GenderResponse findByIdAndDeletedAtIsNull(Long id){
        return genderMapper.toResponse(
                genderRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Gender not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name){
        return genderRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public GenderResponse findByNameAndDeletedAtIsNull(String name){
        return genderMapper.toResponse(
                genderRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(()->new ResourceNotFoundException("Gender not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<GenderResponse> findAllActive() {
        return genderRepository.findByDeletedAtIsNull()
                .stream()
                .map(genderMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        Gender gender = genderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender not exist."));

        gender.setDeletedAt(LocalDateTime.now());

        genderRepository.save(gender);
    }
}
