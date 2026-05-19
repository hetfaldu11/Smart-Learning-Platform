package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.model.user.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Platform> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Platform> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<Platform> findByName(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Platform> findByDeletedAtIsNull();
}
