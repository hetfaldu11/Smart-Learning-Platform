package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseLevel.request.CreateCourseLevelRequest;
import com.fm.smartlearningplatform.course.dto.courseLevel.request.UpdateCourseLevelRequest;
import com.fm.smartlearningplatform.course.dto.courseLevel.response.CourseLevelResponse;
import com.fm.smartlearningplatform.course.mapper.CourseLevelMapper;
import com.fm.smartlearningplatform.course.model.CourseLevel;
import com.fm.smartlearningplatform.course.repository.CourseLevelRepository;
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
public class CourseLevelService {

    private final CourseLevelRepository courseLevelRepository;
    private final CourseLevelMapper courseLevelMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseLevelResponse create(
            CreateCourseLevelRequest request
    ) {

        validateCourseLevelNameNotExist(request.name());

        CourseLevel courseLevel =
                courseLevelMapper.toEntity(request);

        return courseLevelMapper.toResponse(
                courseLevelRepository.save(courseLevel)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseLevelResponse findById(Long courseLevelId) {

        return courseLevelMapper.toResponse(
                getCourseLevel(courseLevelId)
        );
    }

    public Page<CourseLevelResponse> search(
            String keyword,
            Pageable pageable
    ) {

        Page<CourseLevel> courseLevels;

        if (keyword == null || keyword.isBlank()) {

            courseLevels = courseLevelRepository
                    .findByDeletedAtIsNull(pageable);

        } else {

            keyword = keyword.trim();

            courseLevels = courseLevelRepository
                    .findByDeletedAtIsNullAndNameContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return courseLevels.map(
                courseLevelMapper::toResponse
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseLevelResponse update(
            Long courseLevelId,
            UpdateCourseLevelRequest request
    ) {

        CourseLevel courseLevel =
                getCourseLevel(courseLevelId);

        if (
                request.name() != null
                        && courseLevelRepository
                        .existsByIdNotAndNameAndDeletedAtIsNull(
                                courseLevelId,
                                request.name()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course level already exists."
            );
        }

        courseLevelMapper.update(
                request,
                courseLevel
        );

        return courseLevelMapper.toResponse(
                courseLevelRepository.save(courseLevel)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseLevelId) {

        CourseLevel courseLevel =
                getCourseLevel(courseLevelId);

        courseLevel.setDeletedAt(LocalDateTime.now());

        courseLevelRepository.save(courseLevel);
    }

    // ─── Restore ──────────────────────────────────────────────

    @Transactional
    public CourseLevelResponse restore(
            Long courseLevelId
    ) {

        CourseLevel courseLevel =
                courseLevelRepository.findById(courseLevelId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Course level not found."
                                )
                        );

        courseLevel.setDeletedAt(null);

        return courseLevelMapper.toResponse(
                courseLevelRepository.save(courseLevel)
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(Long courseLevelId) {

        return courseLevelRepository
                .existsByIdAndDeletedAtIsNull(courseLevelId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseLevel getCourseLevel(
            Long courseLevelId
    ) {

        return courseLevelRepository
                .findByIdAndDeletedAtIsNull(courseLevelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course level not found."
                        )
                );
    }

    private void validateCourseLevelNameNotExist(
            String name
    ) {

        if (
                courseLevelRepository
                        .existsByNameAndDeletedAtIsNull(name)
        ) {

            throw new DuplicateResourceException(
                    "Course level already exists."
            );
        }
    }
}