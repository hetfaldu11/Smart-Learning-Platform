package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserSocialLink;
import com.fm.smartlearningplatform.model.UserSocialLinkId;

public interface UserSocialLinkService {
    public void save(UserSocialLink userSocialLink);
    public UserSocialLink findById(UserSocialLinkId id);
    public void deleteById(UserSocialLinkId id);
}
