package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.course.request.CreateCourseRequest;
import com.fm.smartlearningplatform.course.dto.course.request.UpdateCourseRequest;
import com.fm.smartlearningplatform.course.dto.course.response.CourseResponse;
import com.fm.smartlearningplatform.course.mapper.CourseMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseLevel;
import com.fm.smartlearningplatform.course.model.CourseStatus;
import com.fm.smartlearningplatform.course.repository.CourseLevelRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.repository.CourseStatusRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final CourseLevelRepository courseLevelRepository;

    private final CourseStatusRepository courseStatusRepository;

    private final CourseMapper courseMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseResponse create(
            CreateCourseRequest request
    ) {

        User instructor = getInstructor(
                request.instructorId()
        );

        CourseLevel courseLevel = getCourseLevel(
                request.courseLevelId()
        );

        CourseStatus courseStatus = getCourseStatus(
                request.courseStatusId()
        );

        Course course =
                courseMapper.toEntity(request);

        course.setInstructor(instructor);

        course.setCourseLevel(courseLevel);

        course.setCourseStatus(courseStatus);

        return courseMapper.toResponse(
                courseRepository.save(course)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseResponse findById(Long courseId) {

        return courseMapper.toResponse(
                getCourse(courseId)
        );
    }

    public Page<CourseResponse> search(
            String keyword,
            Pageable pageable
    ) {

        Page<Course> courses;

        if (keyword == null || keyword.isBlank()) {

            courses = courseRepository
                    .findByDeletedAtIsNull(pageable);

        } else {

            keyword = keyword.trim();

            courses = courseRepository
                    .findByDeletedAtIsNullAndTitleContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return courses.map(
                courseMapper::toResponse
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseResponse update(
            Long courseId,
            UpdateCourseRequest request
    ) {

        Course course = getCourse(courseId);

        if (request.instructorId() != null) {

            User instructor = getInstructor(
                    request.instructorId()
            );

            course.setInstructor(instructor);
        }

        if (request.courseLevelId() != null) {

            CourseLevel courseLevel =
                    getCourseLevel(
                            request.courseLevelId()
                    );

            course.setCourseLevel(courseLevel);
        }

        if (request.courseStatusId() != null) {

            CourseStatus courseStatus =
                    getCourseStatus(
                            request.courseStatusId()
                    );

            course.setCourseStatus(courseStatus);
        }

        courseMapper.update(
                request,
                course
        );

        return courseMapper.toResponse(
                courseRepository.save(course)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseId) {

        Course course = getCourse(courseId);

        course.setDeletedAt(LocalDateTime.now());

        courseRepository.save(course);
    }

    // ─── Restore ──────────────────────────────────────────────

    @Transactional
    public CourseResponse restore(Long courseId) {

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found."
                        )
                );

        course.setDeletedAt(null);

        return courseMapper.toResponse(
                courseRepository.save(course)
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(Long courseId) {

        return courseRepository
                .existsByIdAndDeletedAtIsNull(
                        courseId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private Course getCourse(Long courseId) {

        return courseRepository
                .findByIdAndDeletedAtIsNull(
                        courseId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found."
                        )
                );
    }

    private User getInstructor(Long instructorId) {

        return userRepository
                .findByIdAndDeletedAtIsNull(
                        instructorId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Instructor not found."
                        )
                );
    }

    private CourseLevel getCourseLevel(
            Long courseLevelId
    ) {

        return courseLevelRepository
                .findByIdAndDeletedAtIsNull(
                        courseLevelId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course level not found."
                        )
                );
    }

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
}