package com.fm.smartlearningplatform.section.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.lesson.repository.LessonProgressRepository;
import com.fm.smartlearningplatform.lesson.repository.LessonRepository;
import com.fm.smartlearningplatform.section.dto.sectionProgress.response.SectionProgressResponse;
import com.fm.smartlearningplatform.section.mapper.SectionProgressMapper;
import com.fm.smartlearningplatform.section.model.Section;
import com.fm.smartlearningplatform.section.model.SectionProgress;
import com.fm.smartlearningplatform.section.repository.SectionProgressRepository;
import com.fm.smartlearningplatform.section.repository.SectionRepository;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectionProgressService {

    private final SectionProgressRepository sectionProgressRepository;

    private final SectionRepository sectionRepository;

    private final LessonRepository lessonRepository;

    private final LessonProgressRepository lessonProgressRepository;

    private final UserRepository userRepository;

    private final SectionProgressMapper sectionProgressMapper;

    // ─── Find ─────────────────────────────────────────────────

    public SectionProgressResponse findByUserAndSection(Long userId, Long sectionId) {

        return sectionProgressMapper.toResponse(getSectionProgress(userId, sectionId));
    }

    public Page<SectionProgressResponse> findAllByUser(Long userId, Pageable pageable) {

        return sectionProgressRepository.findByUserId(userId, pageable).map(sectionProgressMapper::toResponse);
    }

    public Page<SectionProgressResponse> findAllBySection(Long sectionId, Pageable pageable) {

        return sectionProgressRepository.findBySectionId(sectionId, pageable).map(sectionProgressMapper::toResponse);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean exists(Long userId, Long sectionId) {

        return sectionProgressRepository.existsByUserIdAndSectionId(userId, sectionId);
    }
    // ─── Refresh Progress ───────────────────────────────────────

    @Transactional
    public void refreshProgress(Long userId, Long sectionId) {

        User user = getUser(userId);

        Section section = getSection(sectionId);

        SectionProgress progress = getOrCreateSectionProgress(user, section);
        long totalLessons = lessonRepository.countBySectionIdAndDeletedAtIsNull(sectionId);

        long completedLessons = lessonProgressRepository.countByUserIdAndLessonSectionIdAndCompletedTrue(userId, sectionId);
        progress.setTotalLessons(Math.toIntExact(totalLessons));

        progress.setCompletedLessons(Math.toIntExact(completedLessons));

        boolean completed = totalLessons > 0 && completedLessons == totalLessons;

        progress.setCompleted(completed);

        progress.setCompletedAt(completed ? java.time.LocalDateTime.now() : null);

        sectionProgressRepository.save(progress);
    }

// ─── Helper ─────────────────────────────────────────────────

    private SectionProgress getOrCreateSectionProgress(User user, Section section) {

        return sectionProgressRepository.findByUserIdAndSectionId(user.getId(), section.getId()).orElseGet(() ->

                SectionProgress.builder().user(user).section(section).completedLessons(0).totalLessons(0).completed(false).build());
    }

    private SectionProgress getSectionProgress(Long userId, Long sectionId) {

        return sectionProgressRepository.findByUserIdAndSectionId(userId, sectionId).orElseThrow(() -> new ResourceNotFoundException("Section progress not found."));
    }

    private User getUser(Long userId) {

        return userRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Section getSection(Long sectionId) {

        return sectionRepository.findByIdAndDeletedAtIsNull(sectionId).orElseThrow(() -> new ResourceNotFoundException("Section not found."));
    }
}
