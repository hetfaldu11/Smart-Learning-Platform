package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Profession> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Profession> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<Profession> findByName(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Profession> findByDeletedAtIsNull();
}