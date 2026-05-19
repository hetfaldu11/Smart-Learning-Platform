package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.EducationLevelMapper;
import com.fm.smartlearningplatform.model.user.EducationLevel;
import com.fm.smartlearningplatform.repository.user.EducationLevelRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationLevelService {

    private final EducationLevelRepository educationLevelRepository;

    private final EducationLevelMapper educationLevelMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public EducationLevelResponse createEducationLevel(CreateEducationLevelRequest request) {
        if (educationLevelRepository.existsByNameAndDeletedAtIsNull(request.getName()))
            throw new DuplicateResourceException("EducationLevel already exists.");

        return educationLevelMapper.toResponse(
                educationLevelRepository.save(
                        educationLevelMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public EducationLevelResponse updateEducationLevel(Long id, UpdateEducationLevelRequest request) {
        EducationLevel educationLevel = educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("EducationLevel not found."));

        if (educationLevelRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.getName()))
            throw new DuplicateResourceException("EducationLevel already exists.");

        educationLevelMapper.updateEducationLevelFromRequest(request, educationLevel);

        return educationLevelMapper.toResponse(educationLevelRepository.save(educationLevel));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return educationLevelRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public EducationLevelResponse findByIdAndDeletedAtIsNull(Long id){
        return educationLevelMapper.toResponse(
                educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("EducationLevel not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name){
        return educationLevelRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public EducationLevelResponse findByNameAndDeletedAtIsNull(String name){
        return educationLevelMapper.toResponse(
                educationLevelRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(()->new ResourceNotFoundException("EducationLevel not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<EducationLevelResponse> findAllActive() {
        return educationLevelRepository.findByDeletedAtIsNull()
                .stream()
                .map(educationLevelMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        EducationLevel educationLevel = educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("EducationLevel not exist."));

        educationLevel.setDeletedAt(LocalDateTime.now());

        educationLevelRepository.save(educationLevel);
    }
}
