package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.*;
import com.fm.smartlearningplatform.repository.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSocialLinkService {

    private final UserRepository userRepository;
    private final UserSocialLinkRepository userSocialLinkRepository;
    private final PlatformRepository platformRepository;

    @Autowired
    public UserSocialLinkService(UserRepository userRepository, UserSocialLinkRepository userSocialLinkRepository, PlatformRepository platformRepository) {
        this.userSocialLinkRepository = userSocialLinkRepository;
        this.platformRepository = platformRepository;
        this.userRepository = userRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserSocialLink createUserSocialLink(User user, Platform platform, String url){

        if(userRepository.existsById(user.getId()) == false)
            throw new RuntimeException("User is not existed.");

        if(platformRepository.existsById(platform.getId()) == false)
            throw new RuntimeException("Platform is not existed.");

        if(userSocialLinkRepository.existsByUserAndPlatform(user,platform))
            throw new RuntimeException("UserSocialLink is already existed.");

        if(url == null)
            throw new RuntimeException("Url is null.");

        UserSocialLink userSocialLink= user.addLink(platform,url);

        userRepository.save(user);

        return userSocialLink;
    }

    @Transactional
    public UserSocialLink addUserSocialLink(Long userId, Long platformId, String url){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User is not existed."));

        Platform platform = platformRepository.findById(platformId)
                .orElseThrow(() -> new RuntimeException("Platform is not existed."));

        if(url == null)
            throw new RuntimeException("Url is null.");

        if(userSocialLinkRepository.existsByUserAndPlatform(user,platform))
            throw new RuntimeException("UserSocialLink is already existed.");

        UserSocialLink userSocialLink = user.addLink(platform,url);

        userRepository.save(user);

        return userSocialLink;
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserSocialLink> findByUserId(Long id){
        if(userRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("User is not exists.");

        return userSocialLinkRepository.findByUserId(id);
    }

    public boolean existByUserIdAndPlatformId(Long userId,Long platformId){
        return userSocialLinkRepository.existsByUserIdAndPlatformId(userId,platformId);
    }

    public UserSocialLink findByUserIdAndPlatformId(Long userId,Long platformId){
        if(userRepository.existsByIdAndDeletedAtIsNull(userId) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(platformRepository.existsByIdAndDeletedAtIsNull(platformId) == false) {
            throw new RuntimeException("Platform is not exists.");
        }

        return userSocialLinkRepository.findByUserIdAndPlatformId(userId,platformId).orElseThrow(() -> new RuntimeException("UserSocialLink is not existed."));
    }

    public boolean existByUserAndPlatform(User user,Platform platform){
        return userSocialLinkRepository.existsByUserAndPlatform(user,platform);
    }

    public UserSocialLink findByUserAndPlatform(User user,Platform platform){
        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(platformRepository.existsByIdAndDeletedAtIsNull(platform.getId()) == false) {
            throw new RuntimeException("Platform is not exists.");
        }
        return userSocialLinkRepository.findByUserAndPlatform(user,platform).orElseThrow(() -> new RuntimeException("UserSocialLink is not existed."));
    }
}