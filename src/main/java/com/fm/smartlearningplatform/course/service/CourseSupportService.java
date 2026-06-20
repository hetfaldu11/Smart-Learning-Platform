package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseSupport.request.CreateCourseSupportRequest;
import com.fm.smartlearningplatform.course.dto.courseSupport.request.UpdateCourseSupportRequest;
import com.fm.smartlearningplatform.course.dto.courseSupport.response.CourseSupportResponse;
import com.fm.smartlearningplatform.course.mapper.CourseSupportMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseSupport;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.repository.CourseSupportRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseSupportService {

    private final CourseSupportRepository courseSupportRepository;
    private final CourseRepository courseRepository;
    private final CourseSupportMapper courseSupportMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseSupportResponse create(CreateCourseSupportRequest request)
    {

        validateCourseSupportNotExist(request.courseId());

        Course course = getCourse(request.courseId());

        CourseSupport courseSupport = courseSupportMapper.toEntity(request);

        courseSupport.setCourse(course);

        return courseSupportMapper.toResponse(
                courseSupportRepository.save(courseSupport)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseSupportResponse findByCourseId(Long courseId)
    {

        return courseSupportMapper.toResponse(getCourseSupport(courseId));
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseSupportResponse update(Long courseId, UpdateCourseSupportRequest request)
    {

        CourseSupport courseSupport = getCourseSupport(courseId);

        courseSupportMapper.update(request, courseSupport);

        return courseSupportMapper.toResponse(courseSupportRepository.save(courseSupport));
    }

    // ─── Delete ───────────────────────────────────────────────

//    @Transactional
//    public void delete(Long courseId) {
//
//        CourseSupport courseSupport =
//                getCourseSupport(courseId);
//
//        courseSupportRepository.delete(courseSupport);
//    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsByCourseId(
            Long courseId
    ) {

        return courseSupportRepository.existsByCourseIdAndCourseDeletedAtIsNull(courseId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseSupport getCourseSupport(
            Long courseId
    ) {

        return courseSupportRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course support not found."
                        )
                );
    }

    private Course getCourse(Long courseId) {

        return courseRepository.findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found."));
    }

    private void validateCourseSupportNotExist(Long courseId)
    {

        if (courseSupportRepository.existsByCourseIdAndCourseDeletedAtIsNull(courseId))
        {
            throw new DuplicateResourceException("Course support already exists.");
        }
    }
}