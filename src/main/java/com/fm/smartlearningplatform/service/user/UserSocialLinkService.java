package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSocialLink;

public interface UserSocialLinkService {
    public void save(UserSocialLink userSocialLink);
    public UserSocialLink findById(Long id);
    public void deleteById(Long id);
    public UserSocialLink findByUserAndPlatform(User user, Platform platform);
    public UserSocialLink findByUserIdAndPlatform(Long userId, Platform platform);
}
