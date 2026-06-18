package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseLevel.request.CreateCourseLevelRequest;
import com.fm.smartlearningplatform.course.dto.courseLevel.request.UpdateCourseLevelRequest;
import com.fm.smartlearningplatform.course.dto.courseLevel.response.CourseLevelResponse;
import com.fm.smartlearningplatform.course.service.CourseLevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-levels")
@RequiredArgsConstructor
public class CourseLevelController {

    private final CourseLevelService courseLevelService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseLevelResponse>
    createCourseLevel(
            @Valid
            @RequestBody
            CreateCourseLevelRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseLevelService.create(request)
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseLevelResponse>
    getCourseLevelById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseLevelService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<CourseLevelResponse>>
    getCourseLevels(
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
                courseLevelService.search(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseLevelResponse>
    updateCourseLevelById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseLevelRequest request
    ) {

        return ResponseEntity.ok(
                courseLevelService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseLevelById(
            @PathVariable Long id
    ) {

        courseLevelService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ─── Restore ──────────────────────────────────────────────

    @PatchMapping("/{id}/restore")
    public ResponseEntity<CourseLevelResponse>
    restoreCourseLevelById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseLevelService.restore(id)
        );
    }
}