package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseStatus.request.CreateCourseStatusRequest;
import com.fm.smartlearningplatform.course.dto.courseStatus.request.UpdateCourseStatusRequest;
import com.fm.smartlearningplatform.course.dto.courseStatus.response.CourseStatusResponse;
import com.fm.smartlearningplatform.course.service.CourseStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-status")
@RequiredArgsConstructor
public class CourseStatusController {

    private final CourseStatusService courseStatusService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseStatusResponse>
    createCourseStatus(
            @Valid
            @RequestBody
            CreateCourseStatusRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseStatusService.create(request)
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseStatusResponse>
    getCourseStatusById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseStatusService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<CourseStatusResponse>>
    getCourseStatuses(
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
                courseStatusService.search(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseStatusResponse>
    updateCourseStatusById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseStatusRequest request
    ) {

        return ResponseEntity.ok(
                courseStatusService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseStatusById(
            @PathVariable Long id
    ) {

        courseStatusService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ─── Restore ──────────────────────────────────────────────

    @PatchMapping("/{id}/restore")
    public ResponseEntity<CourseStatusResponse>
    restoreCourseStatusById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseStatusService.restore(id)
        );
    }
}