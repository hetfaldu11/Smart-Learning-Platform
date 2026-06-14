package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.enrollmentStatus.request.CreateEnrollmentStatusRequest;
import com.fm.smartlearningplatform.course.dto.enrollmentStatus.request.UpdateEnrollmentStatusRequest;
import com.fm.smartlearningplatform.course.dto.enrollmentStatus.response.EnrollmentStatusResponse;
import com.fm.smartlearningplatform.course.service.EnrollmentStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollment-status")
@RequiredArgsConstructor
public class EnrollmentStatusController {

    private final EnrollmentStatusService
            enrollmentStatusService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<EnrollmentStatusResponse>
    createEnrollmentStatus(
            @Valid
            @RequestBody
            CreateEnrollmentStatusRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        enrollmentStatusService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentStatusResponse>
    getEnrollmentStatusById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                enrollmentStatusService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<EnrollmentStatusResponse>>
    getEnrollmentStatuses(
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
                enrollmentStatusService.search(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentStatusResponse>
    updateEnrollmentStatusById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateEnrollmentStatusRequest request
    ) {

        return ResponseEntity.ok(
                enrollmentStatusService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteEnrollmentStatusById(
            @PathVariable Long id
    ) {

        enrollmentStatusService.delete(id);

        return ResponseEntity.noContent().build();
    }
}