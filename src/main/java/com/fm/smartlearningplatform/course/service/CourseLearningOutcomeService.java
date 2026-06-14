package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request.CreateCourseLearningOutcomeRequest;
import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request.UpdateCourseLearningOutcomeRequest;
import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.response.CourseLearningOutcomeResponse;
import com.fm.smartlearningplatform.course.mapper.CourseLearningOutcomeMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseLearningOutcome;
import com.fm.smartlearningplatform.course.repository.CourseLearningOutcomeRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseLearningOutcomeService {

    private final CourseLearningOutcomeRepository
            courseLearningOutcomeRepository;

    private final CourseRepository courseRepository;

    private final CourseLearningOutcomeMapper
            courseLearningOutcomeMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseLearningOutcomeResponse create(
            CreateCourseLearningOutcomeRequest request
    ) {

        validateLearningOutcomeNotExist(
                request.courseId(),
                request.outcome()
        );

        Course course = getCourse(
                request.courseId()
        );

        CourseLearningOutcome courseLearningOutcome =
                courseLearningOutcomeMapper.toEntity(
                        request
                );

        courseLearningOutcome.setCourse(course);

        return courseLearningOutcomeMapper.toResponse(
                courseLearningOutcomeRepository.save(
                        courseLearningOutcome
                )
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseLearningOutcomeResponse findById(
            Long courseLearningOutcomeId
    ) {

        return courseLearningOutcomeMapper.toResponse(
                getCourseLearningOutcome(
                        courseLearningOutcomeId
                )
        );
    }

    public Page<CourseLearningOutcomeResponse> findByCourseId(
            Long courseId,
            Pageable pageable
    ) {

        return courseLearningOutcomeRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId,
                        pageable
                )
                .map(
                        courseLearningOutcomeMapper
                                ::toResponse
                );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseLearningOutcomeResponse update(
            Long courseLearningOutcomeId,
            UpdateCourseLearningOutcomeRequest request
    ) {

        CourseLearningOutcome courseLearningOutcome =
                getCourseLearningOutcome(
                        courseLearningOutcomeId
                );

        if (
                request.outcome() != null
                        && courseLearningOutcomeRepository
                        .existsByIdNotAndCourseIdAndOutcomeAndCourseDeletedAtIsNull(
                                courseLearningOutcomeId,
                                courseLearningOutcome
                                        .getCourse()
                                        .getId(),
                                request.outcome()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course learning outcome already exists."
            );
        }

        courseLearningOutcomeMapper.update(
                request,
                courseLearningOutcome
        );

        return courseLearningOutcomeMapper.toResponse(
                courseLearningOutcomeRepository.save(
                        courseLearningOutcome
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(
            Long courseLearningOutcomeId
    ) {

        CourseLearningOutcome courseLearningOutcome =
                getCourseLearningOutcome(
                        courseLearningOutcomeId
                );

        courseLearningOutcomeRepository.delete(
                courseLearningOutcome
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long courseLearningOutcomeId
    ) {

        return courseLearningOutcomeRepository
                .existsByIdAndCourseDeletedAtIsNull(
                        courseLearningOutcomeId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseLearningOutcome getCourseLearningOutcome(
            Long courseLearningOutcomeId
    ) {

        return courseLearningOutcomeRepository
                .findByIdAndCourseDeletedAtIsNull(
                        courseLearningOutcomeId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course learning outcome not found."
                        )
                );
    }

    private Course getCourse(Long courseId) {

        return courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found."
                        )
                );
    }

    private void validateLearningOutcomeNotExist(
            Long courseId,
            String outcome
    ) {

        if (
                courseLearningOutcomeRepository
                        .existsByCourseIdAndOutcomeAndCourseDeletedAtIsNull(
                                courseId,
                                outcome
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course learning outcome already exists."
            );
        }
    }
}