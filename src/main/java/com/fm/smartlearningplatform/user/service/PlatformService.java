package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.response.DeletePlatformResponse;
import com.fm.smartlearningplatform.user.dto.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.user.dto.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.user.mapper.PlatformMapper;
import com.fm.smartlearningplatform.user.model.Platform;
import com.fm.smartlearningplatform.user.model.Platform;
import com.fm.smartlearningplatform.user.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PlatformResponse create(CreatePlatformRequest request) {
        validatePlatformNotExist(request.name());
        return platformMapper.toResponse(platformRepository.save(platformMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public PlatformResponse update(Long id, UpdatePlatformRequest request) {

        Platform platform = getPlatform(id);

        if (platformRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Platform already exist.");

        platformMapper.updatePlatformFromRequest(request, platform);
        return platformMapper.toResponse(platformRepository.save(platform));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return platformRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public PlatformResponse findById(Long id) {
        return platformMapper.toResponse(getPlatform(id));
    }

    @Transactional(readOnly = true)
    public Page<PlatformResponse> searchByKeyword(String keyword, Pageable pageable)
    {
        Page<Platform> platforms;
        if (keyword == null || keyword.isBlank()) {
            platforms = platformRepository.findByDeletedAtIsNull(pageable);
        } else {
            keyword = keyword.trim();
            platforms = platformRepository .findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }
        return platforms.map(platformMapper::toResponse);
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeletePlatformResponse deleteById(Long id) {
        Platform platform = getPlatform(id);

        platform.setDeletedAt(LocalDateTime.now());

        platformRepository.save(platform);

        return new DeletePlatformResponse("Platform is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validatePlatformNotExist(String name) {
        if (platformRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Platform already exist.");
    }

    private Platform getPlatform(Long id) {
        return platformRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Platform not found."));
    }
}
