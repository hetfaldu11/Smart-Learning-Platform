package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request.CreateAssistantInstructorRoleRequest;
import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request.UpdateAssistantInstructorRoleRequest;
import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.response.AssistantInstructorRoleResponse;
import com.fm.smartlearningplatform.course.service.AssistantInstructorRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assistant-instructor-roles")
@RequiredArgsConstructor
public class AssistantInstructorRoleController {

    private final AssistantInstructorRoleService
            assistantInstructorRoleService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<AssistantInstructorRoleResponse>
    createAssistantInstructorRole(
            @Valid
            @RequestBody
            CreateAssistantInstructorRoleRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        assistantInstructorRoleService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<AssistantInstructorRoleResponse>
    getAssistantInstructorRoleById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                assistantInstructorRoleService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<AssistantInstructorRoleResponse>>
    getAssistantInstructorRoles(
            @RequestParam(
                    value = "q",
                    required = false
            )
            String keyword,

            @PageableDefault(
                    size = 10,
                    sort = "name"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                assistantInstructorRoleService.search(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<AssistantInstructorRoleResponse>
    updateAssistantInstructorRoleById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateAssistantInstructorRoleRequest request
    ) {

        return ResponseEntity.ok(
                assistantInstructorRoleService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteAssistantInstructorRoleById(
            @PathVariable Long id
    ) {

        assistantInstructorRoleService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ─── Restore ──────────────────────────────────────────────

    @PatchMapping("/{id}/restore")
    public ResponseEntity<AssistantInstructorRoleResponse>
    restoreAssistantInstructorRoleById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                assistantInstructorRoleService.restore(id)
        );
    }
}