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
    public UserInterest createUserInterest(User user, Interest interest){

        if(userRepository.existsById(user.getId()) == false)
            throw new RuntimeException("User is not existed.");

        if(interestRepository.existsById(interest.getId()) == false)
            throw new RuntimeException("Interest is not existed.");

        if(userInterestRepository.existsByUserAndInterest(user,interest))
            throw new RuntimeException("UserInterest is already existed.");

        UserInterest userInterest= user.addInterest(interest);

        userRepository.save(user);

        return userInterest;
    }

    @Transactional
    public UserInterest addInterest(Long userId, Long interestId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User is not existed."));

        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("Interest is not existed."));

        if(userInterestRepository.existsByUserAndInterest(user,interest))
            throw new RuntimeException("UserInterest is already existed.");

        UserInterest userInterest = user.addInterest(interest);

        userRepository.save(user);

        return userInterest;
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserInterest> findByUserId(Long id){
        if(userRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("User is not exists.");

        return userInterestRepository.findByUserId(id);
    }

    public List<UserInterest> findByInterestId(Long id){
        if(interestRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("Interest is not exists.");

        return userInterestRepository.findByUserId(id);
    }

    public boolean existByUserIdAndInterestId(Long userId,Long interestId){
        return userInterestRepository.existsByUserIdAndInterestId(userId,interestId);
    }

    public UserInterest findByUserIdAndInterestId(Long userId,Long interestId){
        if(userRepository.existsByIdAndDeletedAtIsNull(userId) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(interestRepository.existsByIdAndDeletedAtIsNull(interestId) == false) {
            throw new RuntimeException("interest  is Not exists.");
        }

        return userInterestRepository.findByUserIdAndInterestId(userId,interestId).orElseThrow(() -> new RuntimeException("UserInterest is not existed."));
    }

    public boolean existByUserAndInterest(User user,Interest interest){
        return userInterestRepository.existsByUserAndInterest(user,interest);
    }

    public UserInterest findByUserAndInterest(User user,Interest interest){
        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(interestRepository.existsByIdAndDeletedAtIsNull(interest.getId()) == false) {
            throw new RuntimeException("Interest is not exists.");
        }
        return userInterestRepository.findByUserAndInterest(user,interest).orElseThrow(() -> new RuntimeException("UserInterest is not existed."));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        if(userInterestRepository.existsById(id) == false)
            throw new RuntimeException("UserInterest is not existed.");

        userInterestRepository.deleteById(id);
    }
}
