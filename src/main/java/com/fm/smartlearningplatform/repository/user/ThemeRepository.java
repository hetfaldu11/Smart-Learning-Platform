package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Theme> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Theme> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<Theme> findByName(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Theme> findByDeletedAtIsNull();
}