package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.userSocialLink.request.CreateUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.request.PatchUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.response.DeleteUserSocialLinkResponse;
import com.fm.smartlearningplatform.user.dto.userSocialLink.response.UserSocialLinkResponse;
import com.fm.smartlearningplatform.user.mapper.UserSocialLinkMapper;
import com.fm.smartlearningplatform.user.model.Platform;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserSocialLink;
import com.fm.smartlearningplatform.user.repository.PlatformRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.user.repository.UserSocialLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSocialLinkService {

    private final UserSocialLinkRepository userSocialLinkRepository;
    private final UserRepository userRepository;
    private final PlatformRepository platformRepository;
    private final UserSocialLinkMapper userSocialLinkMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserSocialLinkResponse create(Long userId, CreateUserSocialLinkRequest request) {

        User user = getUser(userId);

        Platform platform = getPlatform(request.platformId());

        validateUserSocialLinkNotExist(userId, request.platformId());

        UserSocialLink userSocialLink = userSocialLinkMapper.toEntity(request, user, platform);

        return userSocialLinkMapper.toResponse(userSocialLinkRepository.save(userSocialLink));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UserSocialLinkResponse> findAll(Long userId) {
        return userSocialLinkRepository
                .findByUserIdAndDeletedAtIsNull(userId)
                .stream()
                .map(userSocialLinkMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserSocialLinkResponse findById(Long userId, Long platformId) {
        return userSocialLinkMapper.toResponse(getUserSocialLink(userId, platformId));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public UserSocialLinkResponse update(Long userId, Long platformId, PatchUserSocialLinkRequest request) {
        UserSocialLink userSocialLink = getUserSocialLink(userId, platformId);

        userSocialLinkMapper.update(request, userSocialLink);

        return userSocialLinkMapper.toResponse(userSocialLinkRepository.save(userSocialLink));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteUserSocialLinkResponse deleteById(Long userId, Long platformId) {
        UserSocialLink userSocialLink = getUserSocialLink(userId, platformId);
        userSocialLink.setDeletedAt(LocalDateTime.now());
        return new DeleteUserSocialLinkResponse("Social link deleted successfully.");
    }

    // ─── Delete ────────────────────────────────────────────────

    private void validateUserSocialLinkNotExist(Long userId, Long platformId) {
        if (userSocialLinkRepository.existsByUserIdAndPlatformIdAndDeletedAtIsNull(userId, platformId)) {
            throw new DuplicateResourceException("Social link already exists.");
        }
    }

    private UserSocialLink getUserSocialLink(Long userId, Long platformId) {
        return userSocialLinkRepository.findByUserIdAndPlatformIdAndDeletedAtIsNull(userId, platformId)
                .orElseThrow(() -> new DuplicateResourceException("Social link not found."));
    }

    private User getUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Platform getPlatform(Long id) {
        return platformRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Platform not found."));
    }
}