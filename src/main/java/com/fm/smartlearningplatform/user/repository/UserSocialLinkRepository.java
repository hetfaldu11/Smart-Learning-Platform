package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.UserSocialLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSocialLinkRepository
        extends JpaRepository<UserSocialLink, Long> {

    boolean existsByUserIdAndPlatformIdAndDeletedAtIsNull(Long userId, Long platformId);

    Optional<UserSocialLink> findByIdAndDeletedAtIsNull(Long id);

    List<UserSocialLink> findByUserIdAndDeletedAtIsNull(Long userId);

    Optional<UserSocialLink>
    findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    Optional<UserSocialLink> findByUserIdAndPlatformIdAndDeletedAtIsNull(Long userId, Long platformId);
}