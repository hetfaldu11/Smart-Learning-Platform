package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserInterest;
import com.fm.smartlearningplatform.user.repository.InterestRepository;
import com.fm.smartlearningplatform.user.repository.UserInterestRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInterestService {

    private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    @Autowired
    public UserInterestService(UserInterestRepository userInterestRepository, UserRepository userRepository, InterestRepository interestRepository) {
        this.userInterestRepository = userInterestRepository;
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserInterest createUserInterest(User user, Interest interest) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(user.getId()))
            throw new ResourceNotFoundException("User not found.");

        if (!interestRepository.existsByIdAndDeletedAtIsNull(interest.getId()))
            throw new ResourceNotFoundException("Interest not found.");

        if (userInterestRepository.existsByUserAndInterest(user, interest))
            throw new DuplicateResourceException("UserInterest already exists.");

        return userInterestRepository.save(UserInterest.builder()
                .user(user)
                .interest(interest)
                .build());
    }

    @Transactional
    public UserInterest addInterest(Long userId, Long interestId) {

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(interestId)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found."));

        if (userInterestRepository.existsByUserAndInterest(user, interest))
            throw new DuplicateResourceException("UserInterest already exists.");

        return userInterestRepository.save(UserInterest.builder()
                .user(user)
                .interest(interest)
                .build());
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserInterest> findByUserId(Long id) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(id))
            throw new ResourceNotFoundException("User not found.");

        return userInterestRepository.findByUserId(id);
    }

    public List<UserInterest> findByInterestId(Long id) {
        if (!interestRepository.existsByIdAndDeletedAtIsNull(id))
            throw new ResourceNotFoundException("Interest not found.");

        return userInterestRepository.findByInterestId(id);
    }

    public boolean existByUserIdAndInterestId(Long userId, Long interestId) {
        return userInterestRepository.existsByUserIdAndInterestId(userId, interestId);
    }

    public UserInterest findByUserIdAndInterestId(Long userId, Long interestId) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
            throw new ResourceNotFoundException("User not found.");
        }

        if (!interestRepository.existsByIdAndDeletedAtIsNull(interestId)) {
            throw new ResourceNotFoundException("Interest not found.");
        }

        return userInterestRepository.findByUserIdAndInterestId(userId, interestId)
                .orElseThrow(() -> new ResourceNotFoundException("UserInterest not found."));
    }

    public boolean existByUserAndInterest(User user, Interest interest) {
        return userInterestRepository.existsByUserAndInterest(user, interest);
    }

    public UserInterest findByUserAndInterest(User user, Interest interest) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(user.getId())) {
            throw new ResourceNotFoundException("User not found.");
        }

        if (!interestRepository.existsByIdAndDeletedAtIsNull(interest.getId())) {
            throw new ResourceNotFoundException("Interest not found.");
        }
        return userInterestRepository.findByUserAndInterest(user, interest)
                .orElseThrow(() -> new ResourceNotFoundException("UserInterest not found."));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id) {
        if (!userInterestRepository.existsById(id))
            throw new ResourceNotFoundException("UserInterest not found.");

        userInterestRepository.deleteById(id);
    }
}
