package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseRequirement.request.CreateCourseRequirementRequest;
import com.fm.smartlearningplatform.course.dto.courseRequirement.request.UpdateCourseRequirementRequest;
import com.fm.smartlearningplatform.course.dto.courseRequirement.response.CourseRequirementResponse;
import com.fm.smartlearningplatform.course.mapper.CourseRequirementMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseRequirement;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.repository.CourseRequirementRepository;
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
public class CourseRequirementService {

    private final CourseRequirementRepository courseRequirementRepository;
    private final CourseRepository courseRepository;
    private final CourseRequirementMapper courseRequirementMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseRequirementResponse create(
            CreateCourseRequirementRequest request
    ) {

        validateRequirementNotExist(
                request.courseId(),
                request.requirement()
        );

        Course course = getCourse(request.courseId());

        CourseRequirement courseRequirement =
                courseRequirementMapper.toEntity(request);

        courseRequirement.setCourse(course);

        return courseRequirementMapper.toResponse(
                courseRequirementRepository.save(courseRequirement)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseRequirementResponse findById(
            Long courseRequirementId
    ) {

        return courseRequirementMapper.toResponse(
                getCourseRequirement(courseRequirementId)
        );
    }

    public Page<CourseRequirementResponse> findByCourseId(
            Long courseId,
            Pageable pageable
    ) {

        return courseRequirementRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId,
                        pageable
                )
                .map(courseRequirementMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseRequirementResponse update(
            Long courseRequirementId,
            UpdateCourseRequirementRequest request
    ) {

        CourseRequirement courseRequirement = getCourseRequirement(courseRequirementId);

        courseRequirementMapper.update(
                request,
                courseRequirement
        );

        return courseRequirementMapper.toResponse(
                courseRequirementRepository.save(
                        courseRequirement
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

//    @Transactional
//    public void delete(Long courseRequirementId) {
//
//        CourseRequirement courseRequirement =
//                getCourseRequirement(courseRequirementId);
//
//        courseRequirementRepository.delete(
//                courseRequirement
//        );
//    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long courseRequirementId
    ) {

        return courseRequirementRepository
                .existsById(courseRequirementId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseRequirement getCourseRequirement(Long courseRequirementId)
    {
        return courseRequirementRepository
                .findById(courseRequirementId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course requirement not found.")
                );
    }

    private Course getCourse(Long courseId) {

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found."));
    }

    private void validateRequirementNotExist(Long courseId, String requirement)
    {

        if (courseRequirementRepository.existsByIdAndCourseDeletedAtIsNull(courseId))
        {
            throw new DuplicateResourceException("Course requirement already exists.");
        }
    }
}