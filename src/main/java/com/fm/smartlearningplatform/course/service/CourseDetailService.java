package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseDetail.request.CreateCourseDetailRequest;
import com.fm.smartlearningplatform.course.dto.courseDetail.request.UpdateCourseDetailRequest;
import com.fm.smartlearningplatform.course.dto.courseDetail.response.CourseDetailResponse;
import com.fm.smartlearningplatform.course.mapper.CourseDetailMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseDetail;
import com.fm.smartlearningplatform.course.repository.CourseDetailRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseDetailService {

    private final CourseDetailRepository courseDetailRepository;

    private final CourseRepository courseRepository;

    private final CourseDetailMapper courseDetailMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseDetailResponse create(
            CreateCourseDetailRequest request
    ) {

        validateCourseDetailNotExist(
                request.courseId()
        );

        Course course = getCourse(
                request.courseId()
        );

        CourseDetail courseDetail =
                courseDetailMapper.toEntity(request);

        courseDetail.setCourse(course);

        return courseDetailMapper.toResponse(
                courseDetailRepository.save(courseDetail)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseDetailResponse findByCourseId(
            Long courseId
    ) {

        return courseDetailMapper.toResponse(
                getCourseDetail(courseId)
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseDetailResponse update(
            Long courseId,
            UpdateCourseDetailRequest request
    ) {

        CourseDetail courseDetail =
                getCourseDetail(courseId);

        courseDetailMapper.update(
                request,
                courseDetail
        );

        return courseDetailMapper.toResponse(
                courseDetailRepository.save(courseDetail)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseId) {

        CourseDetail courseDetail =
                getCourseDetail(courseId);

        courseDetailRepository.delete(courseDetail);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsByCourseId(
            Long courseId
    ) {

       return  courseDetailRepository
                .existsByCourseIdAndCourseDeletedAtIsNull(courseId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseDetail getCourseDetail(
            Long courseId
    ) {

        return courseDetailRepository
                .findByCourseIdAndCourseDeletedAtIsNull(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course detail not found."
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

    private void validateCourseDetailNotExist(
            Long courseId
    ) {

        if (
                courseDetailRepository
                        .existsByCourseIdAndCourseDeletedAtIsNull(courseId)
        ) {

            throw new DuplicateResourceException(
                    "Course detail already exists."
            );
        }
    }
}