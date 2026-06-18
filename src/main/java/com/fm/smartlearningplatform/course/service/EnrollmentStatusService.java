package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.enrollmentStatus.request.CreateEnrollmentStatusRequest;
import com.fm.smartlearningplatform.course.dto.enrollmentStatus.request.UpdateEnrollmentStatusRequest;
import com.fm.smartlearningplatform.course.dto.enrollmentStatus.response.EnrollmentStatusResponse;
import com.fm.smartlearningplatform.course.mapper.EnrollmentStatusMapper;
import com.fm.smartlearningplatform.course.model.EnrollmentStatus;
import com.fm.smartlearningplatform.course.repository.EnrollmentStatusRepository;
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
public class EnrollmentStatusService {

    private final EnrollmentStatusRepository
            enrollmentStatusRepository;

    private final EnrollmentStatusMapper
            enrollmentStatusMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public EnrollmentStatusResponse create(
            CreateEnrollmentStatusRequest request
    ) {

        validateEnrollmentStatusNotExist(
                request.name()
        );

        EnrollmentStatus enrollmentStatus =
                enrollmentStatusMapper.toEntity(
                        request
                );

        return enrollmentStatusMapper.toResponse(
                enrollmentStatusRepository.save(
                        enrollmentStatus
                )
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public EnrollmentStatusResponse findById(
            Long enrollmentStatusId
    ) {

        return enrollmentStatusMapper.toResponse(
                getEnrollmentStatus(
                        enrollmentStatusId
                )
        );
    }

    public Page<EnrollmentStatusResponse> search(
            String keyword,
            Pageable pageable
    ) {

        Page<EnrollmentStatus> enrollmentStatuses;

        if (keyword == null || keyword.isBlank()) {

            enrollmentStatuses =
                    enrollmentStatusRepository.findAll(
                            pageable
                    );

        } else {

            keyword = keyword.trim();

            enrollmentStatuses =
                    enrollmentStatusRepository
                            .findByNameContainingIgnoreCase(
                                    keyword,
                                    pageable
                            );
        }

        return enrollmentStatuses.map(
                enrollmentStatusMapper::toResponse
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public EnrollmentStatusResponse update(
            Long enrollmentStatusId,
            UpdateEnrollmentStatusRequest request
    ) {

        EnrollmentStatus enrollmentStatus =
                getEnrollmentStatus(
                        enrollmentStatusId
                );

        if (
                request.name() != null
                        && enrollmentStatusRepository
                        .existsByIdNotAndName(
                                enrollmentStatusId,
                                request.name()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Enrollment status already exists."
            );
        }

        enrollmentStatusMapper.update(
                request,
                enrollmentStatus
        );

        return enrollmentStatusMapper.toResponse(
                enrollmentStatusRepository.save(
                        enrollmentStatus
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(
            Long enrollmentStatusId
    ) {

        EnrollmentStatus enrollmentStatus =
                getEnrollmentStatus(
                        enrollmentStatusId
                );

        enrollmentStatusRepository.delete(
                enrollmentStatus
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long enrollmentStatusId
    ) {

        return enrollmentStatusRepository
                .existsById(
                        enrollmentStatusId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

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

    private void validateEnrollmentStatusNotExist(
            String name
    ) {

        if (
                enrollmentStatusRepository
                        .existsByName(name)
        ) {

            throw new DuplicateResourceException(
                    "Enrollment status already exists."
            );
        }
    }
}