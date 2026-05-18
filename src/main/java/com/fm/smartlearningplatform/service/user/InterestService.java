package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.repository.user.InterestRepository;
import com.fm.smartlearningplatform.repository.user.UserInterestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;

    @Autowired
    public InterestService (InterestRepository interestRepository, UserInterestRepository userInterestRepository) {
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Interest createInterest(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(interestRepository.existsByName(name))
            throw new RuntimeException("Interest is already exist.");

        Interest interest = Interest.builder()
                .name(name)
                .build();

        return interestRepository.save(interest);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Interest updateInterest(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Interest is not exist."));

        interest.setName(newName);

        return interestRepository.save(interest);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return interestRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Interest findByIdAndDeletedAtIsNull(Long id){
        Interest interest = interestRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Interest is not existed."));

        if(interest.getDeletedAt() != null)
            throw new RuntimeException("Interest is deleted.");
        return interest;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return interestRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Interest findByNameAndDeletedAtIsNull(String name){
        Interest interest = interestRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Interest is not existed."));

        if(interest.getDeletedAt() != null)
            throw new RuntimeException("Interest is deleted.");
        return interest;
    }

    List<Interest> findByDeletedAtIsNull(){
        return interestRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Interest interest = interestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest is not exist."));

        if(interest.getDeletedAt() != null){
            throw  new RuntimeException("Interest is already deleted.");
        }

        userInterestRepository.deleteByInterestId(id);

        interest.setDeletedAt(LocalDateTime.now());

        interestRepository.save(interest);
    }
}
