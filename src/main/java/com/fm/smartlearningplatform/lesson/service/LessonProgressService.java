package com.fm.smartlearningplatform.lesson.service;


import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.lesson.dto.lessonProgress.request.CreateLessonProgressRequest;
import com.fm.smartlearningplatform.lesson.dto.lessonProgress.response.LessonProgressResponse;
import com.fm.smartlearningplatform.lesson.mapper.LessonProgressMapper;
import com.fm.smartlearningplatform.lesson.model.Lesson;
import com.fm.smartlearningplatform.lesson.model.LessonProgress;
import com.fm.smartlearningplatform.lesson.repository.LessonProgressRepository;
import com.fm.smartlearningplatform.lesson.repository.LessonRepository;
import com.fm.smartlearningplatform.section.service.SectionProgressService;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;

    private final LessonRepository lessonRepository;

    private final UserRepository userRepository;

    private final LessonProgressMapper lessonProgressMapper;

    private final SectionProgressService sectionProgressService;

    // ─── Save Progress ────────────────────────────────────────

    // ─── Save Progress ───────────────────────────────────────────

    @Transactional
    public LessonProgressResponse saveProgress(CreateLessonProgressRequest request, Long userId) {

        User user = getUser(userId);

        Lesson lesson = getLesson(request.lessonId());

        LessonProgress lessonProgress = getOrCreateLessonProgress(user, lesson);

        boolean wasCompleted = lessonProgress.getCompleted();

        // Never decrease watched seconds
        lessonProgress.setWatchedSeconds(Math.max(lessonProgress.getWatchedSeconds(), request.watchedSeconds()));

        // Always save latest playback position
        lessonProgress.setLastPositionSeconds(request.lastPositionSeconds());

        double percentage = calculateProgress(lessonProgress.getWatchedSeconds(), lesson.getDurationSeconds());

        lessonProgress.setProgressPercentage(percentage);

        boolean completed = percentage >= 100.0;

        lessonProgress.setCompleted(completed);

        if (completed) {

            if (lessonProgress.getCompletedAt() == null) {

                lessonProgress.setCompletedAt(LocalDateTime.now());
            }

        } else {

            lessonProgress.setCompletedAt(null);
        }

        LessonProgress saved = lessonProgressRepository.save(lessonProgress);

        /*
         * Refresh section only when lesson completion status changes.
         */
        if (wasCompleted != completed) {

            sectionProgressService.refreshProgress(user.getId(), lesson.getSection().getId());

            /*
             * Later
             *
             * courseProgressService.refreshProgress(
             *      user.getId(),
             *      lesson.getSection().getCourse().getId()
             * );
             */
        }

        return lessonProgressMapper.toResponse(saved);
    }
    // ─── Find ────────────────────────────────────────────────────

    public LessonProgressResponse findByUserAndLesson(Long userId, Long lessonId) {

        return lessonProgressMapper.toResponse(getLessonProgress(userId, lessonId));
    }

    public Page<LessonProgressResponse> findAllByUser(Long userId, Pageable pageable) {

        getUser(userId);

        return lessonProgressRepository.findByUserId(userId, pageable).map(lessonProgressMapper::toResponse);
    }

    public Page<LessonProgressResponse> findCompletedByUser(Long userId, Pageable pageable) {

        getUser(userId);

        return lessonProgressRepository.findByUserIdAndCompleted(userId, true, pageable).map(lessonProgressMapper::toResponse);
    }

    public Page<LessonProgressResponse> findInProgressByUser(Long userId, Pageable pageable) {

        getUser(userId);

        return lessonProgressRepository.findByUserIdAndCompletedFalseOrderByUpdatedAtDesc(userId, pageable).map(lessonProgressMapper::toResponse);
    }

// ─── Exists ──────────────────────────────────────────────────

    public boolean exists(Long userId, Long lessonId) {

        return lessonProgressRepository.existsByUserIdAndLessonId(userId, lessonId);
    }
    // ─── Helper ─────────────────────────────────────────────────

    private LessonProgress getOrCreateLessonProgress(User user, Lesson lesson) {

        return lessonProgressRepository.findByUserIdAndLessonId(user.getId(), lesson.getId()).orElseGet(() -> LessonProgress.builder().user(user).lesson(lesson).watchedSeconds(0).lastPositionSeconds(0).progressPercentage(0.0).completed(false).build());
    }

    private LessonProgress getLessonProgress(Long userId, Long lessonId) {

        return lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson progress not found."));
    }

    private User getUser(Long userId) {

        return userRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Lesson getLesson(Long lessonId) {

        return lessonRepository.findByIdAndDeletedAtIsNull(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));
    }

    private double calculateProgress(Integer watchedSeconds, Integer durationSeconds) {

        if (durationSeconds == null || durationSeconds <= 0) {
            return 0.0;
        }

        double percentage = (watchedSeconds * 100.0) / durationSeconds;

        percentage = Math.min(percentage, 100.0);

        return Math.round(percentage * 100.0) / 100.0;
    }

}

