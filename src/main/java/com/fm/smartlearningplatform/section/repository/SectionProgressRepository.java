package com.fm.smartlearningplatform.section.repository;

import com.fm.smartlearningplatform.section.model.SectionProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionProgressRepository extends JpaRepository<SectionProgress, Long> {

    Optional<SectionProgress> findByUserIdAndSectionId(Long userId, Long sectionId);

    boolean existsByUserIdAndSectionId(Long userId, Long sectionId);

    Page<SectionProgress> findByUserId(Long userId, Pageable pageable);

    Page<SectionProgress> findBySectionId(Long sectionId, Pageable pageable);

    long countByUserIdAndCompletedTrue(Long userId);

    long countBySectionIdAndCompletedTrue(Long sectionId);
    Page<SectionProgress> findByUserIdAndCompleted(
            Long userId,
            Boolean completed,
            Pageable pageable
    );
    long countByUserIdAndSectionCourseIdAndCompletedTrue(
            Long userId,
            Long courseId
    );
    long countByUserIdAndSectionCourseId(
            Long userId,
            Long courseId
    );
    Page<SectionProgress> findByUserIdAndSectionCourseId(
            Long userId,
            Long courseId,
            Pageable pageable
    );
}