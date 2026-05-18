package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Gender;
import com.fm.smartlearningplatform.repository.user.GenderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GenderService {
    private final GenderRepository genderRepository;

    @Autowired
    public GenderService (GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Gender createGender(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(genderRepository.existsByName(name))
            throw new RuntimeException("Gender is already exist.");

        Gender gender = Gender.builder()
                .name(name)
                .build();

        return genderRepository.save(gender);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Gender updateGender(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Gender gender = genderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Gender is not exist."));

        gender.setName(newName);

        return genderRepository.save(gender);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return genderRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Gender findByIdAndDeletedAtIsNull(Long id){
        Gender gender = genderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Gender is not existed."));

        if(gender.getDeletedAt() != null)
            throw new RuntimeException("Gender is deleted.");
        return gender;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return genderRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Gender findByNameAndDeletedAtIsNull(String name){
        Gender gender = genderRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Gender is not existed."));

        if(gender.getDeletedAt() != null)
            throw new RuntimeException("Gender is deleted.");
        return gender;
    }

    List<Gender> findByDeletedAtIsNull(){
        return genderRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Gender gender = genderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gender is not exist."));

        if(gender.getDeletedAt() != null){
            throw  new RuntimeException("Gender is already deleted.");
        }

        gender.setDeletedAt(LocalDateTime.now());

        genderRepository.save(gender);
    }
}
