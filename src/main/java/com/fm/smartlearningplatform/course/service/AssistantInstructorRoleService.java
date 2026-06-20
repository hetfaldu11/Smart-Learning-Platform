package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request.CreateAssistantInstructorRoleRequest;
import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request.UpdateAssistantInstructorRoleRequest;
import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.response.AssistantInstructorRoleResponse;
import com.fm.smartlearningplatform.course.mapper.AssistantInstructorRoleMapper;
import com.fm.smartlearningplatform.course.model.AssistantInstructorRole;
import com.fm.smartlearningplatform.course.repository.AssistantInstructorRoleRepository;
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
public class AssistantInstructorRoleService {

    private final AssistantInstructorRoleRepository assistantInstructorRoleRepository;

    private final AssistantInstructorRoleMapper assistantInstructorRoleMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public AssistantInstructorRoleResponse create(
            CreateAssistantInstructorRoleRequest request
    ) {

        validateRoleNameNotExist(request.name());

        AssistantInstructorRole assistantInstructorRole =
                assistantInstructorRoleMapper.toEntity(request);

        return assistantInstructorRoleMapper.toResponse(
                assistantInstructorRoleRepository.save(
                        assistantInstructorRole
                )
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public AssistantInstructorRoleResponse findById(
            Long assistantInstructorRoleId
    ) {

        return assistantInstructorRoleMapper.toResponse(getAssistantInstructorRole(assistantInstructorRoleId));
    }

    public Page<AssistantInstructorRoleResponse> search(String keyword, Pageable pageable
    ) {

        Page<AssistantInstructorRole> roles;

        if (keyword == null || keyword.isBlank()) {

            roles = assistantInstructorRoleRepository
                    .findByDeletedAtIsNull(pageable);

        } else {

            keyword = keyword.trim();

            roles = assistantInstructorRoleRepository
                    .findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }

        return roles.map(assistantInstructorRoleMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public AssistantInstructorRoleResponse update(
            Long assistantInstructorRoleId,
            UpdateAssistantInstructorRoleRequest request
    ) {

        AssistantInstructorRole assistantInstructorRole =
                getAssistantInstructorRole(assistantInstructorRoleId);

        if (request.name() != null && assistantInstructorRoleRepository
                .existsByIdNotAndNameAndDeletedAtIsNull(assistantInstructorRoleId, request.name())
        ) {

            throw new DuplicateResourceException("Assistant instructor role already exists.");
        }
        assistantInstructorRoleMapper.update(request, assistantInstructorRole);

        return assistantInstructorRoleMapper.toResponse(
                assistantInstructorRoleRepository.save(assistantInstructorRole)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(
            Long assistantInstructorRoleId
    ) {

        AssistantInstructorRole assistantInstructorRole =
                getAssistantInstructorRole(
                        assistantInstructorRoleId
                );

        assistantInstructorRole.setDeletedAt(
                LocalDateTime.now()
        );

        assistantInstructorRoleRepository.save(
                assistantInstructorRole
        );
    }

    // ─── Restore ──────────────────────────────────────────────

    @Transactional
    public AssistantInstructorRoleResponse restore(
            Long assistantInstructorRoleId
    ) {

        AssistantInstructorRole assistantInstructorRole =
                assistantInstructorRoleRepository
                        .findById(assistantInstructorRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assistant instructor role not found."
                                )
                        );

        assistantInstructorRole.setDeletedAt(null);

        return assistantInstructorRoleMapper.toResponse(
                assistantInstructorRoleRepository.save(
                        assistantInstructorRole
                )
        );
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(
            Long assistantInstructorRoleId
    ) {

        return assistantInstructorRoleRepository
                .existsByIdAndDeletedAtIsNull(
                        assistantInstructorRoleId
                );
    }

    // ─── Helper ───────────────────────────────────────────────

    private AssistantInstructorRole getAssistantInstructorRole(
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

    private void validateRoleNameNotExist(
            String name
    ) {

        if (
                assistantInstructorRoleRepository
                        .existsByNameAndDeletedAtIsNull(
                                name
                        )
        ) {

            throw new DuplicateResourceException(
                    "Assistant instructor role already exists."
            );
        }
    }
}