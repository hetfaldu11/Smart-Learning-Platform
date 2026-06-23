package com.fm.smartlearningplatform.section.repository;

import com.fm.smartlearningplatform.section.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findByCourseIdAndDeletedAtIsNullOrderByPositionAsc(Long courseId);

    Optional<Section> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCourseIdAndPositionAndDeletedAtIsNull(
            Long courseId,
            Integer position
    );

    long countByCourseIdAndDeletedAtIsNull(Long courseId);

    List<Section> findByCourseIdAndPublishedTrueAndDeletedAtIsNullOrderByPositionAsc(
            Long courseId
    );

    Page<Section> findByCourseIdAndDeletedAtIsNullOrderByPositionAsc(
            Long courseId,
            Pageable pageable
    );

    boolean existsByIdAndDeletedAtIsNull(Long id);
}
