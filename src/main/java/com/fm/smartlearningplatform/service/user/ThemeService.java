package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Theme;
import com.fm.smartlearningplatform.repository.user.ThemeRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;


    @Autowired
    public ThemeService (ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;

    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Theme createTheme(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(themeRepository.existsByName(name))
            throw new RuntimeException("Theme is already exist.");

        Theme theme = Theme.builder()
                .name(name)
                .build();

        return themeRepository.save(theme);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Theme updateTheme(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Theme theme = themeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Theme is not exist."));

        theme.setName(newName);

        return themeRepository.save(theme);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return themeRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Theme findByIdAndDeletedAtIsNull(Long id){
        Theme theme = themeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Theme is not existed."));

        if(theme.getDeletedAt() != null)
            throw new RuntimeException("Theme is deleted.");
        return theme;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return themeRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Theme findByNameAndDeletedAtIsNull(String name){
        Theme theme = themeRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Theme is not existed."));

        if(theme.getDeletedAt() != null)
            throw new RuntimeException("Theme is deleted.");
        return theme;
    }

    List<Theme> findByDeletedAtIsNull(){
        return themeRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theme is not exist."));

        if(theme.getDeletedAt() != null){
            throw  new RuntimeException("Theme is already deleted.");
        }

        theme.setDeletedAt(LocalDateTime.now());

        themeRepository.save(theme);
    }
}
