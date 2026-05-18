package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.repository.user.PlatformRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlatformService {
    private final PlatformRepository platformRepository;

    @Autowired
    public PlatformService (PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Platform createPlatform(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(platformRepository.existsByName(name))
            throw new RuntimeException("Platform is already exist.");

        Platform platform = Platform.builder()
                .name(name)
                .build();

        return platformRepository.save(platform);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Platform updatePlatform(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Platform platform = platformRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Platform is not exist."));

        platform.setName(newName);

        return platformRepository.save(platform);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return platformRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Platform findByIdAndDeletedAtIsNull(Long id){
        Platform platform = platformRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Platform is not existed."));

        if(platform.getDeletedAt() != null)
            throw new RuntimeException("Platform is deleted.");
        return platform;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return platformRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Platform findByNameAndDeletedAtIsNull(String name){
        Platform platform = platformRepository.findByName(name)
                .orElseThrow(()->new RuntimeException("Platform is not existed."));

        if(platform.getDeletedAt() != null)
            throw new RuntimeException("Platform is deleted.");
        return platform;
    }

    List<Platform> findByDeletedAtIsNull(){
        return platformRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Platform is not exist."));

        if(platform.getDeletedAt() != null){
            throw  new RuntimeException("Platform is already deleted.");
        }

        platform.setDeletedAt(LocalDateTime.now());

        platformRepository.save(platform);
    }
}
