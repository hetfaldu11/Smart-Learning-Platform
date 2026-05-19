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
    public Gender createGender(String name){

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

        if(id == null){
            throw new RuntimeException("Id is null");
        }

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }
        if(genderRepository.existsByName(newName))
            throw new RuntimeException("Gender is already exist.");

        Gender gender = genderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Gender is not exist."));

        gender.setName(newName);

        return genderRepository.save(gender);
    }

    // ─── Find ────────────────────────────────────────────────

    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        if(id == null){
            throw new RuntimeException("Id is null");
        }

        return genderRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Gender findByIdAndDeletedAtIsNull(Long id){

        if(id == null){
            throw new RuntimeException("Id is null");
        }

        Gender gender = genderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Gender is not existed."));

        if(gender.getDeletedAt() != null)
            throw new RuntimeException("Gender is deleted.");
        return gender;
    }

    public boolean existsByNameAndDeletedAtIsNull(String name){

        if(name == null){
            throw new RuntimeException("Name is null");
        }

        return genderRepository.existsByNameAndDeletedAtIsNull(name);
    }

    public Gender findByNameAndDeletedAtIsNull(String name){

        if(name == null){
            throw new RuntimeException("Name is null");
        }

        Gender gender = genderRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Gender is not existed."));

        if(gender.getDeletedAt() != null)
            throw new RuntimeException("Gender is deleted.");
        return gender;
    }

    public List<Gender> findByDeletedAtIsNull(){
        return genderRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){

        if(id == null){
            throw new RuntimeException("Id is null");
        }

        Gender gender = genderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gender is not exist."));

        if(gender.getDeletedAt() != null){
            throw  new RuntimeException("Gender is already deleted.");
        }

        gender.setDeletedAt(LocalDateTime.now());

        genderRepository.save(gender);
    }
}
