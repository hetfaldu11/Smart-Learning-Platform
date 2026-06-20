package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseMedia.request.CreateCourseMediaRequest;
import com.fm.smartlearningplatform.course.dto.courseMedia.request.UpdateCourseMediaRequest;
import com.fm.smartlearningplatform.course.dto.courseMedia.response.CourseMediaResponse;
import com.fm.smartlearningplatform.course.mapper.CourseMediaMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseMedia;
import com.fm.smartlearningplatform.course.repository.CourseMediaRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseMediaService {

    private final CourseMediaRepository courseMediaRepository;

    private final CourseRepository courseRepository;

    private final CourseMediaMapper courseMediaMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseMediaResponse create(
            CreateCourseMediaRequest request
    ) {

        validateCourseMediaNotExist(
                request.courseId()
        );

        Course course = getCourse(
                request.courseId()
        );

        CourseMedia courseMedia =
                courseMediaMapper.toEntity(request);

        courseMedia.setCourse(course);

        return courseMediaMapper.toResponse(
                courseMediaRepository.save(courseMedia)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseMediaResponse findByCourseId(
            Long courseId
    ) {

        return courseMediaMapper.toResponse(
                getCourseMedia(courseId)
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseMediaResponse update(
            Long courseId,
            UpdateCourseMediaRequest request
    ) {

        CourseMedia courseMedia =
                getCourseMedia(courseId);

        courseMediaMapper.update(
                request,
                courseMedia
        );

        return courseMediaMapper.toResponse(
                courseMediaRepository.save(courseMedia)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseId) {

        CourseMedia courseMedia =
                getCourseMedia(courseId);
        courseMedia.setDeletedAt(LocalDate.now());

        courseMediaRepository.save(courseMedia);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsByCourseId(
            Long courseId
    ) {

        return courseMediaRepository
                .existsByCourseIdAndCourseDeletedAtIsNull(
                        courseId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseMedia getCourseMedia(
            Long courseId
    ) {

        return courseMediaRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course media not found."
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

    private void validateCourseMediaNotExist(
            Long courseId
    ) {

        if (
                courseMediaRepository
                        .existsByCourseIdAndCourseDeletedAtIsNull(
                                courseId
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course media already exists."
            );
        }
    }
}