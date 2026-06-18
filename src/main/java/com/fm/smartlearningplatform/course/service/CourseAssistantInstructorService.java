package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.request.CreateCourseAssistantInstructorRequest;
import com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.response.CourseAssistantInstructorResponse;
import com.fm.smartlearningplatform.course.mapper.CourseAssistantInstructorMapper;
import com.fm.smartlearningplatform.course.model.AssistantInstructorRole;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseAssistantInstructor;
import com.fm.smartlearningplatform.course.repository.AssistantInstructorRoleRepository;
import com.fm.smartlearningplatform.course.repository.CourseAssistantInstructorRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
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
public class CourseAssistantInstructorService {

    private final CourseAssistantInstructorRepository
            courseAssistantInstructorRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final AssistantInstructorRoleRepository
            assistantInstructorRoleRepository;

    private final CourseAssistantInstructorMapper
            courseAssistantInstructorMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseAssistantInstructorResponse create(
            CreateCourseAssistantInstructorRequest request
    ) {

        validateAssistantInstructorNotExist(
                request.courseId(),
                request.instructorId()
        );

        Course course = getCourse(
                request.courseId()
        );

        User instructor = getInstructor(
                request.instructorId()
        );

        AssistantInstructorRole assistantInstructorRole =
                getAssistantInstructorRole(
                        request.assistantInstructorRoleId()
                );

        CourseAssistantInstructor courseAssistantInstructor = courseAssistantInstructorMapper.toEntity(request);

        courseAssistantInstructor.setCourse(course);

        courseAssistantInstructor.setInstructor(instructor);

        courseAssistantInstructor.setAssistantInstructorRole(assistantInstructorRole);

        return courseAssistantInstructorMapper.toResponse(
                courseAssistantInstructorRepository.save(
                        courseAssistantInstructor
                )
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseAssistantInstructorResponse findById(
            Long courseAssistantInstructorId
    ) {

        return courseAssistantInstructorMapper.toResponse(
                getCourseAssistantInstructor(
                        courseAssistantInstructorId
                )
        );
    }

    public Page<CourseAssistantInstructorResponse> findByCourseId(
            Long courseId,
            Pageable pageable
    ) {

        return courseAssistantInstructorRepository
                .findByCourseId(
                        courseId,
                        pageable
                )
                .map(courseAssistantInstructorMapper::toResponse);
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(
            Long courseAssistantInstructorId
    ) {

        CourseAssistantInstructor courseAssistantInstructor =
                getCourseAssistantInstructor(
                        courseAssistantInstructorId
                );

        courseAssistantInstructorRepository.delete(
                courseAssistantInstructor
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long courseAssistantInstructorId
    ) {

        return courseAssistantInstructorRepository
                .existsById(
                        courseAssistantInstructorId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseAssistantInstructor getCourseAssistantInstructor(
            Long courseAssistantInstructorId
    ) {

        return courseAssistantInstructorRepository
                .findById(courseAssistantInstructorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course assistant instructor not found."
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

    private User getInstructor(Long instructorId) {

        return userRepository.findByIdAndDeletedAtIsNull(
                        instructorId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Instructor not found."
                        )
                );
    }

    private AssistantInstructorRole
    getAssistantInstructorRole(
            Long assistantInstructorRoleId
    ) {

        return assistantInstructorRoleRepository
                .findByIdAndDeletedAtIsNull(
                        assistantInstructorRoleId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assistant instructor role not found."
                        )
                );
    }

    private void validateAssistantInstructorNotExist(
            Long courseId,
            Long instructorId
    ) {

        if (
                courseAssistantInstructorRepository
                        .existsByCourseIdAndInstructorId(
                                courseId,
                                instructorId
                        )
        ) {

            throw new DuplicateResourceException(
                    "Assistant instructor already exists in this course."
            );
        }
    }
}