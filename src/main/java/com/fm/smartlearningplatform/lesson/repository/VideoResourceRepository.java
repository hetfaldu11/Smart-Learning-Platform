package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.VideoResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoResourceRepository extends JpaRepository<VideoResource, Long> {

    // ─── Find ─────────────────────────────────────────────────

    Optional<VideoResource> findById(Long videoResourceId);

    Optional<VideoResource> findByIdAndVideoLessonId(Long videoResourceId, Long videoLessonId);

    Page<VideoResource> findByVideoLessonIdOrderByPositionAsc(Long videoLessonId, Pageable pageable);

    // ─── Exists ───────────────────────────────────────────────

    boolean existsById(Long videoResourceId);

    boolean existsByVideoLessonIdAndFileId(Long videoLessonId, String fileId);

    boolean existsByVideoLessonIdAndPosition(Long videoLessonId, Integer position);

    boolean existsByVideoLessonIdAndPositionAndIdNot(Long videoLessonId, Integer position, Long videoResourceId);

    // ─── Count ────────────────────────────────────────────────

    long countByVideoLessonId(Long videoLessonId);
}