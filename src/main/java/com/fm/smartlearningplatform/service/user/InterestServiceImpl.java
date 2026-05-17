package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.InterestRepository;
import com.fm.smartlearningplatform.repository.user.UserInterestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterestServiceImpl implements InterestService{

    @Autowired
    InterestRepository interestRepository;
    @Autowired
    private UserInterestRepository userInterestRepository;

    //  ─────Create───────────────────────────────────────────

    @Override
    public Interest createInterest(String name) {
        Interest interest = Interest.builder()
                .name(name)
                .build();

        interestRepository.save(interest);
        return interest;
    }

    //  ─────Find─────────────────────────────────────────────

    @Override
    public Interest findById(Long id) {
        return interestRepository.findByIdAndDeletedAtIsNull(id).orElse(null);
    }

    @Override
    public List<Interest> findAll() {
        return interestRepository.findByDeletedAtIsNull();
    }

    //  ─────Delete─────────────────────────────────────────────

    @Override
    public void deleteById(Long id){
        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(id).orElse(null);
        userInterestRepository.deleteByInterestId(id);
        if(interest == null)
            return;
        interest.setDeletedAt(LocalDateTime.now());
        interestRepository.save(interest);
    }
}