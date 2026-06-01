package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.userInterest.request.CreateUserInterestRequest;
import com.fm.smartlearningplatform.user.dto.userInterest.request.CreateUserInterestsRequest;
import com.fm.smartlearningplatform.user.dto.userInterest.response.DeleteUserInterestResponse;
import com.fm.smartlearningplatform.user.dto.userInterest.response.UserInterestResponse;
import com.fm.smartlearningplatform.user.mapper.UserInterestMapper;
import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserInterest;
import com.fm.smartlearningplatform.user.repository.InterestRepository;
import com.fm.smartlearningplatform.user.repository.UserInterestRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserInterestService {

    private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final UserInterestMapper userInterestMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserInterestResponse create(Long userId, CreateUserInterestRequest request) {
        User user = getUser(userId);

        Interest interest = getInterest(request.interestId());

        validateUserInterestNotExist(userId, request.interestId());

        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interest(interest)
                .build();

        return userInterestMapper.toResponse(userInterestRepository.save(userInterest));
    }

    @Transactional
    public List<UserInterestResponse> create(Long userId, CreateUserInterestsRequest request) {

        Set<Long> uniqueInterestIds = new HashSet<>(request.interestIds());

        if (uniqueInterestIds.size() != request.interestIds().size()) {
            throw new DuplicateResourceException("Duplicate interest ids are not allowed.");
        }

        User user = getUser(userId);

        List<Interest> interests = interestRepository.findByIdInAndDeletedAtIsNull(new ArrayList<>(uniqueInterestIds));

        if (interests.size() != uniqueInterestIds.size()) {
            throw new ResourceNotFoundException("Some interest ids do not exist.");
        }

        Set<Long> existingInterestIds = userInterestRepository.findInterestIdsByUserId(userId);

        List<UserInterest> userInterests = new ArrayList<>(interests.size());

        for (Interest interest : interests) {
            if (existingInterestIds.contains(interest.getId())) {
                throw new DuplicateResourceException(
                        "UserInterest already exists."
                );
            }

            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .interest(interest)
                    .build();

            userInterests.add(userInterest);
        }

        return userInterestRepository.saveAll(userInterests)
                .stream()
                .map(userInterestMapper::toResponse)
                .toList();
    }


    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UserInterestResponse> findByUserId(Long userId) {
        validateUserExist(userId);

        return userInterestRepository.findByUserId(userId)
                .stream()
                .map(userInterestMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserInterestResponse> findByInterestId(Long interestId) {
        validateInterestExist(interestId);

        return userInterestRepository.findByInterestId(interestId)
                .stream()
                .map(userInterestMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserInterestResponse findByUserIdAndInterestId(Long userId, Long interestId) {
        validateUserExist(userId);

        validateInterestExist(interestId);

        return userInterestMapper.toResponse(getUserInterest(userId, interestId));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public DeleteUserInterestResponse deleteById(Long userId, Long interestId) {
        validateUserInterestExist(userId,interestId);
        UserInterest userInterest = getUserInterest(userId, interestId);
        userInterestRepository.delete(userInterest);
        return new DeleteUserInterestResponse("User interest association deleted successfully.");
    }
    // ─── Helper ────────────────────────────────────────────────

    private void validateUserInterestNotExist(Long userId, Long interestId) {
        if (userInterestRepository.existsByUserIdAndInterestId(userId, interestId))
            throw new DuplicateResourceException("UserInterest already exists.");
    }

    private void validateUserInterestExist(Long userId, Long interestId) {
        if (!userInterestRepository.existsByUserIdAndInterestId(userId, interestId))
            throw new ResourceNotFoundException("UserInterest not found.");
    }

    private UserInterest getUserInterest(Long userId, Long interestId) {
        return userInterestRepository.findByUserIdAndInterestId(userId, interestId)
                .orElseThrow(() -> new ResourceNotFoundException("User interest not found."));
    }

    private User getUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    private void validateUserExist(Long id) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(id)) {
            throw new DuplicateResourceException("User not found.");
        }
    }

    private Interest getInterest(Long id) {
        return interestRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found."));
    }

    private void validateInterestExist(Long id) {
        if (!interestRepository.existsByIdAndDeletedAtIsNull(id))
            throw new DuplicateResourceException("Interest not found.");
    }
}
