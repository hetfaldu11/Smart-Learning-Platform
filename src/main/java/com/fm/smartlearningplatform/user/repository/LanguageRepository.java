package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Language> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Language> findByNameAndDeletedAtIsNull(String name);

    boolean existsByCodeAndDeletedAtIsNull(String name);

    Optional<Language> findByCodeAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    boolean existsByIdNotAndCodeAndDeletedAtIsNull(Long id, String code);

    List<Language> findByDeletedAtIsNullOrderByNameAsc();

    List<Language> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<Language> findByIdInAndDeletedAtIsNull(List<Long> ids);

    @Query("""
        SELECT l
        FROM Language l
        WHERE l.deletedAt IS NULL
          AND (
                l.name LIKE CONCAT('%', :keyword, '%')
                OR l.code LIKE CONCAT('%', :keyword, '%')
              )
    """)
    List<Language> search(@Param("keyword") String keyword);
}