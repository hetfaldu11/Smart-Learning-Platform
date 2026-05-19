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
    public EducationLevel createEducationLevel(CreateEducationLevelRequest createEducationLevelRequest){

        String name = createEducationLevelRequest.getName();

        EducationLevel educationLevel = EducationLevel.builder()
                .name(name)
                .build();
        return educationLevelRepository.save(educationLevel);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public EducationLevel updateEducationLevel(Long id, String newName) {
        if(id == null){
            throw new RuntimeException("Id is null");
        }

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        if(educationLevelRepository.existsByName(newName))
            throw new RuntimeException("Education level is already exist.");

        EducationLevel educationLevel = educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("EducationLevel is not exist."));

        educationLevel.setName(newName);

        return educationLevelRepository.save(educationLevel);
    }

    // ─── Find ────────────────────────────────────────────────

   public  boolean existsByIdAndDeletedAtIsNull(Long id) {
       if(id == null){
           throw new RuntimeException("Id is null");
       }

        return educationLevelRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public EducationLevel findByIdAndDeletedAtIsNull(Long id){
        EducationLevel educationLevel = educationLevelRepository.findById(id)
                .orElseThrow(()->new RuntimeException("EducationLevel is not existed."));

        if(educationLevel.getDeletedAt() != null)
            throw new RuntimeException("EducationLevel is deleted.");
        return educationLevel;
    }

    public  boolean existsByNameAndDeletedAtIsNull(String name){

        if(name == null){
            throw new RuntimeException("Name is null");
        }

        return educationLevelRepository.existsByNameAndDeletedAtIsNull(name);
    }

    public EducationLevel findByNameAndDeletedAtIsNull(String name){

        if(name == null){
            throw new RuntimeException("Name is null");
        }

        EducationLevel educationLevel = educationLevelRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("EducationLevel is not existed."));

        if(educationLevel.getDeletedAt() != null)
            throw new RuntimeException("EducationLevel is deleted.");
        return educationLevel;
    }

    public List<EducationLevel> findByDeletedAtIsNull(){
        return educationLevelRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){

        if(id == null){
            throw new RuntimeException("Id is null");
        }

        EducationLevel educationLevel = educationLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EducationLevel is not exist."));

        if(educationLevel.getDeletedAt() != null){
            throw  new RuntimeException("EducationLevel is already deleted.");
        }

        educationLevel.setDeletedAt(LocalDateTime.now());

        educationLevelRepository.save(educationLevel);
    }
}


