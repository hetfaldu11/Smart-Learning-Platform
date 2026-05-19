package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface

LanguageRepository extends JpaRepository<Language,Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Language> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Language> findByNameAndDeletedAtIsNull(String name);

    boolean existsByCodeAndDeletedAtIsNull(String code);

    Optional<Language> findByCodeAndDeletedAtIsNull(String code);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<Language> findByName(String name);

    boolean existsByCode(String name);

    boolean existsByIdNotAndCode(Long id, String code);

    Optional<Language> findByCode(String name);

    List<Language> findByDeletedAtIsNull();
}
