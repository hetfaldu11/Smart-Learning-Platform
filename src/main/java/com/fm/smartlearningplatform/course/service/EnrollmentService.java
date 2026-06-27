package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.enrollment.request.CreateEnrollmentRequest;
import com.fm.smartlearningplatform.course.dto.enrollment.request.UpdateEnrollmentRequest;
import com.fm.smartlearningplatform.course.dto.enrollment.response.EnrollmentResponse;
import com.fm.smartlearningplatform.course.mapper.EnrollmentMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.Enrollment;
import com.fm.smartlearningplatform.course.model.EnrollmentStatus;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.repository.EnrollmentRepository;
import com.fm.smartlearningplatform.course.repository.EnrollmentStatusRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final EnrollmentStatusRepository
            enrollmentStatusRepository;

    private final EnrollmentMapper enrollmentMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public EnrollmentResponse create(
            CreateEnrollmentRequest request
    ) {

        validateEnrollmentNotExist(
                request.userId(),
                request.courseId()
        );

        User user = getUser(
                request.userId()
        );

        Course course = getCourse(
                request.courseId()
        );

//        EnrollmentStatus enrollmentStatus =
//                getEnrollmentStatus(
//                        request.enrollmentStatusId()
//                );

        Enrollment enrollment =
                enrollmentMapper.toEntity(request);

        enrollment.setUser(user);

        enrollment.setCourse(course);

//        enrollment.setEnrollmentStatus(
//                enrollmentStatus
//        );

        return enrollmentMapper.toResponse(
                enrollmentRepository.save(enrollment)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public EnrollmentResponse findById(
            Long enrollmentId
    ) {

        return enrollmentMapper.toResponse(
                getEnrollment(enrollmentId)
        );
    }

    public Page<EnrollmentResponse> findByUserId(
            Long userId,
            Pageable pageable
    ) {

        return enrollmentRepository
                .findByUserIdAndCourseDeletedAtIsNull(
                        userId,
                        pageable
                )
                .map(enrollmentMapper::toResponse);
    }

    public Page<EnrollmentResponse> findByCourseId(
            Long courseId,
            Pageable pageable
    ) {

        return enrollmentRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId,
                        pageable
                )
                .map(enrollmentMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public EnrollmentResponse update(
            Long enrollmentId,
            UpdateEnrollmentRequest request
    ) {

        Enrollment enrollment =
                getEnrollment(enrollmentId);

        if (request.enrollmentStatusId() != null) {

            EnrollmentStatus enrollmentStatus =
                    getEnrollmentStatus(
                            request.enrollmentStatusId()
                    );

            enrollment.setEnrollmentStatus(
                    enrollmentStatus
            );
        }

        if (
                request.completedAt() != null
        ) {

            enrollment.setCompletedAt(
                    request.completedAt()
            );
        }

        enrollmentMapper.update(
                request,
                enrollment
        );

        return enrollmentMapper.toResponse(
                enrollmentRepository.save(enrollment)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long enrollmentId) {

        Enrollment enrollment =
                getEnrollment(enrollmentId);

        enrollmentRepository.delete(enrollment);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long enrollmentId
    ) {

        return enrollmentRepository
                .existsById(enrollmentId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private Enrollment getEnrollment(
            Long enrollmentId
    ) {

        return enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found."
                        )
                );
    }

    private User getUser(Long userId) {

        return userRepository
                .findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }

    private Course getCourse(Long courseId) {

        return courseRepository
                .findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found."
                        )
                );
    }

    private EnrollmentStatus getEnrollmentStatus(
            Long enrollmentStatusId
    ) {

        return enrollmentStatusRepository
                .findById(enrollmentStatusId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment status not found."
                        )
                );
    }

    private void validateEnrollmentNotExist(
            Long userId,
            Long courseId
    ) {

        if (
                enrollmentRepository
                        .existsByUserIdAndCourseIdAndCourseDeletedAtIsNull(
                                userId,
                                courseId
                        )
        ) {

            throw new DuplicateResourceException(
                    "User already enrolled in this course."
            );
        }
    }
}