package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseStatus.request.CreateCourseStatusRequest;
import com.fm.smartlearningplatform.course.dto.courseStatus.request.UpdateCourseStatusRequest;
import com.fm.smartlearningplatform.course.dto.courseStatus.response.CourseStatusResponse;
import com.fm.smartlearningplatform.course.mapper.CourseStatusMapper;
import com.fm.smartlearningplatform.course.model.CourseStatus;
import com.fm.smartlearningplatform.course.repository.CourseStatusRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseStatusService {

    private final CourseStatusRepository courseStatusRepository;
    private final CourseStatusMapper courseStatusMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseStatusResponse create(
            CreateCourseStatusRequest request
    ) {

        validateCourseStatusNameNotExist(request.name());

        CourseStatus courseStatus =
                courseStatusMapper.toEntity(request);

        return courseStatusMapper.toResponse(
                courseStatusRepository.save(courseStatus)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseStatusResponse findById(
            Long courseStatusId
    ) {

        return courseStatusMapper.toResponse(
                getCourseStatus(courseStatusId)
        );
    }

    public Page<CourseStatusResponse> search(
            String keyword,
            Pageable pageable
    ) {

        Page<CourseStatus> courseStatuses;

        if (keyword == null || keyword.isBlank()) {

            courseStatuses = courseStatusRepository
                    .findByDeletedAtIsNull(pageable);

        } else {

            keyword = keyword.trim();

            courseStatuses = courseStatusRepository
                    .findByDeletedAtIsNullAndNameContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return courseStatuses.map(
                courseStatusMapper::toResponse
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseStatusResponse update(
            Long courseStatusId,
            UpdateCourseStatusRequest request
    ) {

        CourseStatus courseStatus =
                getCourseStatus(courseStatusId);

        if (
                request.name() != null
                        && courseStatusRepository
                        .existsByIdNotAndNameAndDeletedAtIsNull(
                                courseStatusId,
                                request.name()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course status already exists."
            );
        }

        courseStatusMapper.update(
                request,
                courseStatus
        );

        return courseStatusMapper.toResponse(
                courseStatusRepository.save(courseStatus)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseStatusId) {

        CourseStatus courseStatus =
                getCourseStatus(courseStatusId);

        courseStatus.setDeletedAt(LocalDateTime.now());

        courseStatusRepository.save(courseStatus);
    }

    // ─── Restore ──────────────────────────────────────────────

    @Transactional
    public CourseStatusResponse restore(
            Long courseStatusId
    ) {

        CourseStatus courseStatus =
                courseStatusRepository.findById(courseStatusId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Course status not found."
                                )
                        );

        courseStatus.setDeletedAt(null);

        return courseStatusMapper.toResponse(
                courseStatusRepository.save(courseStatus)
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(Long courseStatusId) {

        return courseStatusRepository
                .existsByIdAndDeletedAtIsNull(
                        courseStatusId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseStatus getCourseStatus(
            Long courseStatusId
    ) {

        return courseStatusRepository
                .findByIdAndDeletedAtIsNull(
                        courseStatusId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course status not found."
                        )
                );
    }

    private void validateCourseStatusNameNotExist(
            String name
    ) {

        if (
                courseStatusRepository
                        .existsByNameAndDeletedAtIsNull(name)
        ) {

            throw new DuplicateResourceException(
                    "Course status already exists."
            );
        }
    }
}