package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Interest> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Interest> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    Optional<Interest> findByName(String name);

    List<Interest> findByDeletedAtIsNull();
}
