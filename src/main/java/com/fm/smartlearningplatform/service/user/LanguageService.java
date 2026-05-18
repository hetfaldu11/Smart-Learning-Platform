package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.repository.user.LanguageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService (LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Language createLanguage(String name, String code){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(code==null) {
            throw new RuntimeException("Code is null.");
        }

        if(languageRepository.existsByName(name))
            throw new RuntimeException("Language is already exist.");

        Language language = Language.builder()
                .name(name)
                .code(code)
                .build();

        return languageRepository.save(language);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Language updateLanguageName(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Language language = languageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Language is not exist."));

        language.setName(newName);

        return languageRepository.save(language);
    }

    @Transactional
    public Language updateLanguageCode(Long id, String newCode) {

        if(newCode==null) {
            throw new RuntimeException("Code is null.");
        }

        Language language = languageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Language is not exist."));

        language.setCode(newCode);

        return languageRepository.save(language);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return languageRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Language findByIdAndDeletedAtIsNull(Long id){
        Language language = languageRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Language is not existed."));

        if(language.getDeletedAt() != null)
            throw new RuntimeException("Language is deleted.");
        return language;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return languageRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Language findByNameAndDeletedAtIsNull(String name){
        Language language = languageRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Language is not existed."));

        if(language.getDeletedAt() != null)
            throw new RuntimeException("Language is deleted.");
        return language;
    }

    boolean existsByCodeAndDeletedAtIsNull(String code){
        return languageRepository.existsByCodeAndDeletedAtIsNull(code);
    }

    Language findByCodeAndDeletedAtIsNull(String code){
        Language language = languageRepository.findByCode(code)
                .orElseThrow(()->new RuntimeException("Language is not existed."));

        if(language.getDeletedAt() != null)
            throw new RuntimeException("Language is deleted.");
        return language;
    }

    List<Language> findByDeletedAtIsNull(){
        return languageRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language is not exist."));

        if(language.getDeletedAt() != null){
            throw  new RuntimeException("Language is already deleted.");
        }

        language.setDeletedAt(LocalDateTime.now());

        languageRepository.save(language);
    } 
}
