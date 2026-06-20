package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseLanguage.request.CreateCourseLanguageRequest;
import com.fm.smartlearningplatform.course.dto.courseLanguage.request.UpdateCourseLanguageRequest;
import com.fm.smartlearningplatform.course.dto.courseLanguage.response.CourseLanguageResponse;
import com.fm.smartlearningplatform.course.mapper.CourseLanguageMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseLanguage;
import com.fm.smartlearningplatform.course.repository.CourseLanguageRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.Language;
import com.fm.smartlearningplatform.user.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseLanguageService {

    private final CourseLanguageRepository
            courseLanguageRepository;

    private final CourseRepository courseRepository;

    private final LanguageRepository languageRepository;

    private final CourseLanguageMapper courseLanguageMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseLanguageResponse create(
            CreateCourseLanguageRequest request
    ) {



        validateCourseLanguageNotExist(
                request.courseId(),
                request.languageId()
        );

        Course course = getCourse(
                request.courseId()
        );

        Language language = getLanguage(
                request.languageId()
        );

        if (request.primary()) {
            removePrimaryLanguage(request.courseId());
        }

        CourseLanguage courseLanguage =
                courseLanguageMapper.toEntity(request);



        courseLanguage.setCourse(course);
        courseLanguage.setLanguage(language);

        return courseLanguageMapper.toResponse(
                courseLanguageRepository.save(courseLanguage)
        );
    }
    // ─── update ─────────────────────────────────────────────────

    @Transactional
    public CourseLanguageResponse updatePrimaryLanguage(Long courseId,UpdateCourseLanguageRequest request) {


        CourseLanguage courseLanguage= courseLanguageRepository.findByCourseIdAndLanguageIdAndCourseDeletedAtIsNull(courseId, request.languageId())
                .orElseThrow(()-> new ResourceNotFoundException("course language not found."));

        removePrimaryLanguage(courseId);

        courseLanguageMapper.update(request,courseLanguage);


        return courseLanguageMapper.toResponse(courseLanguageRepository.save(courseLanguage));

    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseLanguageResponse findById(
            Long courseLanguageId
    ) {

        return courseLanguageMapper.toResponse(
                getCourseLanguage(courseLanguageId)
        );
    }

    public Page<CourseLanguageResponse> findByCourseId(
            Long courseId,
            Pageable pageable
    ) {

        return courseLanguageRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId,
                        pageable
                )
                .map(courseLanguageMapper::toResponse);
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseLanguageId) {

        CourseLanguage courseLanguage =
                getCourseLanguage(courseLanguageId);

        courseLanguageRepository.delete(
                courseLanguage
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long courseLanguageId
    ) {

        return courseLanguageRepository
                .existsById(courseLanguageId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseLanguage getCourseLanguage(
            Long courseLanguageId
    ) {

        return courseLanguageRepository
                .findById(courseLanguageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course language not found."
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

    private Language getLanguage(Long languageId) {

        return languageRepository
                .findByIdAndDeletedAtIsNull(languageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Language not found."
                        )
                );
    }

    private void validateCourseLanguageNotExist(
            Long courseId,
            Long languageId
    ) {

        if (
                courseLanguageRepository
                        .existsByCourseIdAndLanguageIdAndCourseDeletedAtIsNull(
                                courseId,
                                languageId
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course language already exists."
            );
        }
    }

    private void removePrimaryLanguage(
            Long courseId
    ) {

        courseLanguageRepository
                .findByCourseIdAndPrimaryTrueAndCourseDeletedAtIsNull(
                        courseId
                )
                .ifPresent(courseLanguage -> {

                    courseLanguage.setPrimary(false);

                    courseLanguageRepository.save(
                            courseLanguage
                    );
                });
    }
}