package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserSocialLink;
import com.fm.smartlearningplatform.model.UserSocialLinkId;
import com.fm.smartlearningplatform.repository.UserSocialLinkRepository;
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
    public UserSocialLink findById(UserSocialLinkId id) {
       return userSocialLinkRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(UserSocialLinkId id) {
        userSocialLinkRepository.deleteById(id);
    }
}
