package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.EducationLevel;
import com.fm.smartlearningplatform.repository.user.EducationLevelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EducationLevelService {
    private final EducationLevelRepository educationLevelRepository;

    @Autowired
    public EducationLevelService (EducationLevelRepository educationLevelRepository) {
        this.educationLevelRepository = educationLevelRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    EducationLevel createEducationLevel(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }
        if(educationLevelRepository.existsByName(name))
            throw new RuntimeException("EducationLevel is already exist.");

        EducationLevel educationLevel = EducationLevel.builder()
                .name(name)
                .build();

        return educationLevelRepository.save(educationLevel);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public EducationLevel updateEducationLevel(Long id, String newName) {
        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }
        EducationLevel educationLevel = educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("EducationLevel is not exist."));

        educationLevel.setName(newName);

        return educationLevelRepository.save(educationLevel);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return educationLevelRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public EducationLevel findByIdAndDeletedAtIsNull(Long id){
        EducationLevel educationLevel = educationLevelRepository.findById(id)
                .orElseThrow(()->new RuntimeException("EducationLevel is not existed."));

        if(educationLevel.getDeletedAt() != null)
            throw new RuntimeException("EducationLevel is deleted.");
        return educationLevel;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return educationLevelRepository.existsByNameAndDeletedAtIsNull(name);
    }

    EducationLevel findByNameAndDeletedAtIsNull(String name){
        EducationLevel educationLevel = educationLevelRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("EducationLevel is not existed."));

        if(educationLevel.getDeletedAt() != null)
            throw new RuntimeException("EducationLevel is deleted.");
        return educationLevel;
    }

    List<EducationLevel> findByDeletedAtIsNull(){
        return educationLevelRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        EducationLevel educationLevel = educationLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EducationLevel is not exist."));

        if(educationLevel.getDeletedAt() != null){
            throw  new RuntimeException("EducationLevel is already deleted.");
        }

        educationLevel.setDeletedAt(LocalDateTime.now());

        educationLevelRepository.save(educationLevel);
    }
}
