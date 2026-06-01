package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.user.mapper.PlatformMapper;
import com.fm.smartlearningplatform.user.model.Platform;
import com.fm.smartlearningplatform.user.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;

    private final PlatformMapper platformMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public PlatformResponse createPlatform(CreatePlatformRequest request) {
        if (platformRepository.existsByNameAndDeletedAtIsNull(request.name()))
            throw new DuplicateResourceException("Platform already exists.");

        return platformMapper.toResponse(
                platformRepository.save(
                        platformMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public PlatformResponse updatePlatform(Long id, UpdatePlatformRequest request) {
        Platform platform = platformRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Platform not found."));

        if (platformRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Platform already exists.");

        platformMapper.updatePlatformFromRequest(request, platform);

        return platformMapper.toResponse(platformRepository.save(platform));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return platformRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public PlatformResponse findByIdAndDeletedAtIsNull(Long id) {
        return platformMapper.toResponse(
                platformRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Platform not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return platformRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public PlatformResponse findByNameAndDeletedAtIsNull(String name) {
        return platformMapper.toResponse(
                platformRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Platform not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<PlatformResponse> findAllActive() {
        return platformRepository.findByDeletedAtIsNull()
                .stream()
                .map(platformMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id) {
        Platform platform = platformRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Platform not exist."));

        platform.setDeletedAt(LocalDateTime.now());

        platformRepository.save(platform);
    }
}
