package com.fm.smartlearningplatform.lesson.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.BadRequestException;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.lesson.dto.lesson.request.CreateLessonRequest;
import com.fm.smartlearningplatform.lesson.dto.lesson.request.UpdateLessonRequest;
import com.fm.smartlearningplatform.lesson.dto.lesson.response.LessonResponse;
import com.fm.smartlearningplatform.lesson.mapper.LessonMapper;
import com.fm.smartlearningplatform.lesson.model.Lesson;
import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import com.fm.smartlearningplatform.lesson.repository.LessonRepository;
import com.fm.smartlearningplatform.section.model.Section;
import com.fm.smartlearningplatform.section.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;

    private final SectionRepository sectionRepository;

    private final LessonMapper lessonMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public LessonResponse create(CreateLessonRequest request) {

        validatePositionNotExist(request.sectionId(), request.position());

        Section section = getSection(request.sectionId());

        Lesson lesson = lessonMapper.toEntity(request);

        lesson.setSection(section);

        return lessonMapper.toResponse(lessonRepository.save(lesson));
    }

    // ─── Find ─────────────────────────────────────────────────

    public LessonResponse findById(Long lessonId) {
        return lessonMapper.toResponse(getLesson(lessonId));
    }

    public Page<LessonResponse> findBySectionId(Long sectionId, Pageable pageable) {
        getSection(sectionId);
        return lessonRepository.findBySectionIdAndDeletedAtIsNullOrderByPositionAsc(sectionId, pageable).map(lessonMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public LessonResponse update(Long lessonId, UpdateLessonRequest request) {

        Lesson lesson = getLesson(lessonId);

        if (request.position() != null && !request.position().equals(lesson.getPosition())) {
            validatePositionForUpdate(lesson.getSection().getId(), request.position(), lessonId);
        }

        LessonStatus status = request.status() != null ? request.status() : lesson.getStatus();

        LocalDateTime publishedAt = request.publishedAt() != null ? request.publishedAt() : lesson.getPublishedAt();

        LocalDateTime scheduledAt = request.scheduledAt() != null ? request.scheduledAt() : lesson.getScheduledAt();

        validateLessonStatus(status, publishedAt, scheduledAt);

        lessonMapper.update(request, lesson);

        return lessonMapper.toResponse(lessonRepository.save(lesson));
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long lessonId) {

        Lesson lesson = getLesson(lessonId);

        lesson.setDeletedAt(LocalDateTime.now());

        lessonRepository.save(lesson);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(Long lessonId) {

        return lessonRepository.existsByIdAndDeletedAtIsNull(lessonId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private Lesson getLesson(Long lessonId) {

        return lessonRepository.findByIdAndDeletedAtIsNull(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));
    }

    private Section getSection(Long sectionId) {

        return sectionRepository.findByIdAndDeletedAtIsNull(sectionId).orElseThrow(() -> new ResourceNotFoundException("Section not found."));
    }

    private void validatePositionNotExist(Long sectionId, Integer position) {

        if (lessonRepository.existsBySectionIdAndPositionAndDeletedAtIsNull(sectionId, position)) {

            throw new DuplicateResourceException("Lesson position already exists in this section.");
        }
    }

    private void validatePositionForUpdate(Long sectionId, Integer position, Long lessonId) {

        if (lessonRepository.existsBySectionIdAndPositionAndIdNotAndDeletedAtIsNull(sectionId, position, lessonId)) {

            throw new DuplicateResourceException("Lesson position already exists in this section.");
        }
    }

    private void validateLessonStatus(LessonStatus status, LocalDateTime publishedAt, LocalDateTime scheduledAt) {

        switch (status) {

            case DRAFT -> {

                if (publishedAt != null) {

                    throw new BadRequestException("Draft lesson cannot have a published date.");
                }

                if (scheduledAt != null) {

                    throw new BadRequestException("Draft lesson cannot have a scheduled date.");
                }
            }

            case PUBLISHED -> {

                if (publishedAt == null) {

                    throw new BadRequestException("Published lesson must have a published date.");
                }

                if (scheduledAt != null) {

                    throw new BadRequestException("Published lesson cannot have a scheduled date.");
                }
            }

            case SCHEDULED -> {

                if (scheduledAt == null) {

                    throw new BadRequestException("Scheduled lesson must have a scheduled date.");
                }

                if (!scheduledAt.isAfter(LocalDateTime.now())) {

                    throw new BadRequestException("Scheduled date must be in the future.");
                }

                if (publishedAt != null) {

                    throw new BadRequestException("Scheduled lesson cannot have a published date.");
                }
            }

            default -> throw new BadRequestException("Invalid lesson status.");
        }
    }
}