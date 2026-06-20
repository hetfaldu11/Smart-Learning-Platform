package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.courseMessageType.request.CreateCourseMessageTypeRequest;
import com.fm.smartlearningplatform.course.dto.courseMessageType.request.UpdateCourseMessageTypeRequest;
import com.fm.smartlearningplatform.course.dto.courseMessageType.response.CourseMessageTypeResponse;
import com.fm.smartlearningplatform.course.mapper.CourseMessageTypeMapper;
import com.fm.smartlearningplatform.course.model.CourseMessageType;
import com.fm.smartlearningplatform.course.repository.CourseMessageTypeRepository;
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
public class CourseMessageTypeService {

    private final CourseMessageTypeRepository courseMessageTypeRepository;
    private final CourseMessageTypeMapper courseMessageTypeMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseMessageTypeResponse create(
            CreateCourseMessageTypeRequest request
    ) {

        validateMessageTypeNameNotExist(request.name());

        CourseMessageType courseMessageType =
                courseMessageTypeMapper.toEntity(request);

        return courseMessageTypeMapper.toResponse(
                courseMessageTypeRepository.save(courseMessageType)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseMessageTypeResponse findById(
            Long courseMessageTypeId
    ) {

        return courseMessageTypeMapper.toResponse(
                getCourseMessageType(courseMessageTypeId)
        );
    }

    public Page<CourseMessageTypeResponse> search(
            String keyword,
            Pageable pageable
    ) {

        Page<CourseMessageType> messageTypes;

        if (keyword == null || keyword.isBlank()) {

            messageTypes = courseMessageTypeRepository
                    .findByDeletedAtIsNull(pageable);

        } else {

            keyword = keyword.trim();

            messageTypes = courseMessageTypeRepository
                    .findByDeletedAtIsNullAndNameContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return messageTypes.map(
                courseMessageTypeMapper::toResponse
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CourseMessageTypeResponse update(
            Long courseMessageTypeId,
            UpdateCourseMessageTypeRequest request
    ) {

        CourseMessageType courseMessageType =
                getCourseMessageType(courseMessageTypeId);

        if (
                request.name() != null
                        && courseMessageTypeRepository
                        .existsByIdNotAndNameAndDeletedAtIsNull(
                                courseMessageTypeId,
                                request.name()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Course message type already exists."
            );
        }

        courseMessageTypeMapper.update(
                request,
                courseMessageType
        );

        return courseMessageTypeMapper.toResponse(
                courseMessageTypeRepository.save(courseMessageType)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseMessageTypeId) {

        CourseMessageType courseMessageType =
                getCourseMessageType(courseMessageTypeId);

        courseMessageType.setDeletedAt(LocalDateTime.now());

        courseMessageTypeRepository.save(courseMessageType);
    }

    // ─── Restore ──────────────────────────────────────────────

    @Transactional
    public CourseMessageTypeResponse restore(
            Long courseMessageTypeId
    ) {

        CourseMessageType courseMessageType =
                courseMessageTypeRepository.findById(
                                courseMessageTypeId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Course message type not found."
                                )
                        );
        if(courseMessageType.getDeletedAt()==null)
        {
            throw  new DuplicateResourceException("this message was already restored");
        }

        courseMessageType.setDeletedAt(null);

        return courseMessageTypeMapper.toResponse(
                courseMessageTypeRepository.save(courseMessageType)
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long courseMessageTypeId
    ) {

        return courseMessageTypeRepository
                .existsByIdAndDeletedAtIsNull(
                        courseMessageTypeId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

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

    private void validateMessageTypeNameNotExist(
            String name
    ) {

        if (
                courseMessageTypeRepository
                        .existsByNameAndDeletedAtIsNull(name)
        ) {

            throw new DuplicateResourceException(
                    "Course message type already exists."
            );
        }
    }
}