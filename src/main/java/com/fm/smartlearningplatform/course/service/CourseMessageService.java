package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseMessage.request.CreateCourseMessageRequest;
import com.fm.smartlearningplatform.course.dto.courseMessage.request.UpdateCourseMessageRequest;
import com.fm.smartlearningplatform.course.dto.courseMessage.response.CourseMessageResponse;
import com.fm.smartlearningplatform.course.mapper.CourseMessageMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseMessage;
import com.fm.smartlearningplatform.course.model.CourseMessageType;
import com.fm.smartlearningplatform.course.repository.CourseMessageRepository;
import com.fm.smartlearningplatform.course.repository.CourseMessageTypeRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
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
public class CourseMessageService {

    private final CourseMessageRepository
            courseMessageRepository;

    private final CourseRepository courseRepository;

    private final CourseMessageTypeRepository
            courseMessageTypeRepository;

    private final CourseMessageMapper courseMessageMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseMessageResponse create(
            CreateCourseMessageRequest request
    ) {

        validateCourseMessageNotExist(
                request.courseId(),
                request.courseMessageTypeId()
        );

        Course course = getCourse(
                request.courseId()
        );

        CourseMessageType courseMessageType =
                getCourseMessageType(
                        request.courseMessageTypeId()
                );

        CourseMessage courseMessage =
                courseMessageMapper.toEntity(request);

        courseMessage.setCourse(course);

        courseMessage.setCourseMessageType(
                courseMessageType
        );

        return courseMessageMapper.toResponse(
                courseMessageRepository.save(
                        courseMessage
                )
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseMessageResponse findById(
            Long courseMessageId
    ) {

        return courseMessageMapper.toResponse(
                getCourseMessage(courseMessageId)
        );
    }

    public Page<CourseMessageResponse> findByCourseId(
            Long courseId,
            Pageable pageable
    ) {

        return courseMessageRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId,
                        pageable
                )
                .map(courseMessageMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseMessageResponse update(
            Long courseMessageId,
            UpdateCourseMessageRequest request
    ) {

        CourseMessage courseMessage =
                getCourseMessage(courseMessageId);

        if (
                request.courseMessageTypeId() != null
                        && courseMessageRepository
                        .existsByCourseIdAndCourseMessageTypeIdAndCourseDeletedAtIsNull(
                                courseMessage.getCourse().getId(),
                                request.courseMessageTypeId()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course message type already exists for this course."
            );
        }

        if (request.courseMessageTypeId() != null) {

            CourseMessageType courseMessageType =
                    getCourseMessageType(
                            request.courseMessageTypeId()
                    );

            courseMessage.setCourseMessageType(
                    courseMessageType
            );
        }

        courseMessageMapper.update(
                request,
                courseMessage
        );

        return courseMessageMapper.toResponse(
                courseMessageRepository.save(
                        courseMessage
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseMessageId) {

        CourseMessage courseMessage =
                getCourseMessage(courseMessageId);

        courseMessageRepository.delete(
                courseMessage
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long courseMessageId
    ) {

        return courseMessageRepository
                .existsByIdAndCourseDeletedAtIsNull(
                        courseMessageId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private CourseMessage getCourseMessage(
            Long courseMessageId
    ) {

        return courseMessageRepository
                .findByIdAndCourseDeletedAtIsNull(
                        courseMessageId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course message not found."
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

    private CourseMessageType getCourseMessageType(
            Long courseMessageTypeId
    ) {

        return courseMessageTypeRepository
                .findByIdAndDeletedAtIsNull(
                        courseMessageTypeId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course message type not found."
                        )
                );
    }

    private void validateCourseMessageNotExist(
            Long courseId,
            Long courseMessageTypeId
    ) {

        if (
                courseMessageRepository
                        .existsByCourseIdAndCourseMessageTypeIdAndCourseDeletedAtIsNull(
                                courseId,
                                courseMessageTypeId
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course message already exists for this message type."
            );
        }
    }
}