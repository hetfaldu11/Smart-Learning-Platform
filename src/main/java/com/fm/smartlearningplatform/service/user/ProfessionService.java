package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.repository.user.ProfessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfessionService {
    private final ProfessionRepository professionRepository;

    @Autowired
    public ProfessionService (ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Profession createProfession(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(professionRepository.existsByName(name))
            throw new RuntimeException("Profession is already exist.");

        Profession profession = Profession.builder()
                .name(name)
                .build();

        return professionRepository.save(profession);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Profession updateProfession(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Profession profession = professionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Profession is not exist."));

        profession.setName(newName);

        return professionRepository.save(profession);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return professionRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Profession findByIdAndDeletedAtIsNull(Long id){
        Profession profession = professionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Profession is not existed."));

        if(profession.getDeletedAt() != null)
            throw new RuntimeException("Profession is deleted.");
        return profession;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return professionRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Profession findByNameAndDeletedAtIsNull(String name){
        Profession profession = professionRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Profession is not existed."));

        if(profession.getDeletedAt() != null)
            throw new RuntimeException("Profession is deleted.");
        return profession;
    }

    List<Profession> findByDeletedAtIsNull(){
        return professionRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Profession profession = professionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profession is not exist."));

        if(profession.getDeletedAt() != null){
            throw  new RuntimeException("Profession is already deleted.");
        }

        profession.setDeletedAt(LocalDateTime.now());

        professionRepository.save(profession);
    }
}
