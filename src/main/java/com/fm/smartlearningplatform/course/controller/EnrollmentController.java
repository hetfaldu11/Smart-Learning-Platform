package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.enrollment.request.CreateEnrollmentRequest;
import com.fm.smartlearningplatform.course.dto.enrollment.request.UpdateEnrollmentRequest;
import com.fm.smartlearningplatform.course.dto.enrollment.response.EnrollmentResponse;
import com.fm.smartlearningplatform.course.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<EnrollmentResponse>
    createEnrollment(
            @Valid
            @RequestBody
            CreateEnrollmentRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        enrollmentService.create(request)
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse>
    getEnrollmentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                enrollmentService.findById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<EnrollmentResponse>>
    getEnrollmentsByUserId(
            @PathVariable Long userId,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                enrollmentService.findByUserId(
                        userId,
                        pageable
                )
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<EnrollmentResponse>>
    getEnrollmentsByCourseId(
            @PathVariable Long courseId,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                enrollmentService.findByCourseId(
                        courseId,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponse>
    updateEnrollmentById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateEnrollmentRequest request
    ) {

        return ResponseEntity.ok(
                enrollmentService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteEnrollmentById(
            @PathVariable Long id
    ) {

        enrollmentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}