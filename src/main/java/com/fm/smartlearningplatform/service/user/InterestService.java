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
    public Interest createInterest(String name){

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

        if(id == null){
            throw new RuntimeException("Id is null");
        }

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }
        if(interestRepository.existsByName(newName))
            throw new RuntimeException("Interest is already exist.");

        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Interest is not exist."));

        interest.setName(newName);

        return interestRepository.save(interest);
    }

    // ─── Find ────────────────────────────────────────────────

   public  boolean existsByIdAndDeletedAtIsNull(Long id) {

       if(id == null){
           throw new RuntimeException("Id is null");
       }

        return interestRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Interest findByIdAndDeletedAtIsNull(Long id){

        if(id == null){
            throw new RuntimeException("Id is null");
        }

        Interest interest = interestRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Interest is not existed."));

        if(interest.getDeletedAt() != null)
            throw new RuntimeException("Interest is deleted.");
        return interest;
    }

   public  boolean existsByNameAndDeletedAtIsNull(String name){

       if(name == null){
           throw new RuntimeException("Name is null");
       }

        return interestRepository.existsByNameAndDeletedAtIsNull(name);
    }

    public Interest findByNameAndDeletedAtIsNull(String name){

        if(name == null){
            throw new RuntimeException("Name is null");
        }

        Interest interest = interestRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Interest is not existed."));

        if(interest.getDeletedAt() != null)
            throw new RuntimeException("Interest is deleted.");
        return interest;
    }

     public List<Interest> findByDeletedAtIsNull(){
        return interestRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){

        if(id == null){
            throw new RuntimeException("Id is null");
        }

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
