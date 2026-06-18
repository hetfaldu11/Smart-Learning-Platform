package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.course.request.CreateCourseRequest;
import com.fm.smartlearningplatform.course.dto.course.request.UpdateCourseRequest;
import com.fm.smartlearningplatform.course.dto.course.response.CourseResponse;
import com.fm.smartlearningplatform.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseResponse>
    createCourse(
            @Valid
            @RequestBody
            CreateCourseRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseService.create(request)
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse>
    getCourseById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>>
    getCourses(
            @RequestParam(
                    value = "q",
                    required = false
            )
            String keyword,

            @PageableDefault(
                    size = 10,
                    sort = "createdAt"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                courseService.search(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse>
    updateCourseById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseRequest request
    ) {

        return ResponseEntity.ok(
                courseService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseById(
            @PathVariable Long id
    ) {

        courseService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ─── Restore ──────────────────────────────────────────────

    @PatchMapping("/{id}/restore")
    public ResponseEntity<CourseResponse>
    restoreCourseById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseService.restore(id)
        );
    }
}