package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSocialLinkRepository extends JpaRepository<UserSocialLink, Long>{

    // ─── Find ────────────────────────────────────────────────

    List<UserSocialLink> findByUserId(Long userId);

    boolean existsByUserIdAndPlatformId(Long userId, Long platformId);

    Optional<UserSocialLink> findByUserIdAndPlatformId(Long userId, Long platformId);

    boolean existsByUserAndPlatform(User user, Platform platform);

    Optional<UserSocialLink> findByUserAndPlatform(User user, Platform platform);
}