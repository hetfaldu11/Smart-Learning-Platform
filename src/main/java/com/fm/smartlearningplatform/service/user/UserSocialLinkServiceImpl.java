package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSocialLink;
import com.fm.smartlearningplatform.repository.user.UserSocialLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSocialLinkServiceImpl implements UserSocialLinkService{

    @Autowired
    UserSocialLinkRepository userSocialLinkRepository;

    @Override
    public void save(UserSocialLink userSocialLink) {
        userSocialLinkRepository.save(userSocialLink);
    }

    @Override
    public UserSocialLink findById(Long id) {
       return userSocialLinkRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userSocialLinkRepository.deleteById(id);
    }

    @Override
    public UserSocialLink findByUserAndPlatform(User user, Platform platform) {
        return userSocialLinkRepository.findByUserAndPlatform(user,platform).orElse(null);
    }

    @Override
    public UserSocialLink findByUserIdAndPlatform(Long userId, Platform platform) {
        return userSocialLinkRepository.findByUserIdAndPlatform(userId,platform).orElse(null);
    }
}