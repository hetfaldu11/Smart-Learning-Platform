package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSocialLinkRepository extends JpaRepository<UserSocialLink, Long>{

    Optional<UserSocialLink> findByUserAndPlatform(User user, Platform platform);
    Optional<UserSocialLink> findByUserIdAndPlatform(Long userId, Platform platform);
}