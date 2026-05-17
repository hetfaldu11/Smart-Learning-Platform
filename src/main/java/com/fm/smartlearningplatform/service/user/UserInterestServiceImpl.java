package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserInterest;
import com.fm.smartlearningplatform.repository.user.InterestRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserInterestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInterestServiceImpl implements UserInterestService{

    private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    @Autowired
    public UserInterestServiceImpl(UserInterestRepository userInterestRepository, UserRepository userRepository, InterestRepository interestRepository) {
        this.userInterestRepository = userInterestRepository;
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
    }

    // ─── Assign Interest to User ────────────────────────────────

    @Transactional
    public UserInterest addInterestToUser(Long userId, Long interestId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(interestId)
                .orElseThrow(() -> new RuntimeException("Interest not found: " + interestId));

        // Prevent duplicate
        if (userInterestRepository.existsByUserIdAndInterestId(userId, interestId)) {
            throw new RuntimeException("User already has this interest");
        }

        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interest(interest)
                .build();

        return userInterestRepository.save(userInterest);
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserInterest> getUserInterests(Long userId) {
        return userInterestRepository.findByUserId(userId);
    }

    public List<UserInterest> getInterestUsers(Long interestId) {
        return userInterestRepository.findByInterestId(interestId);
    }

    public UserInterest findByUserIdAndInterestId(Long userId, Long interestId) {
        return userInterestRepository.findByUserIdAndInterestId(userId, interestId)
                .orElse(null);
    }

    // ─── Remove Interest from User ──────────────────────────────

    @Transactional
    public void removeInterestFromUser(Long userId, Long interestId) {
        if (!userInterestRepository.existsByUserIdAndInterestId(userId, interestId)) {
            throw new RuntimeException("User does not have this interest");
        }
        userInterestRepository.deleteByUserId(userId); // or specific row
    }

    @Transactional
    public void deleteById(Long id) {
        userInterestRepository.deleteById(id);
    }

}
