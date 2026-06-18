package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request.CreateCourseLearningOutcomeRequest;
import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.request.UpdateCourseLearningOutcomeRequest;
import com.fm.smartlearningplatform.course.dto.courseLearningOutcome.response.CourseLearningOutcomeResponse;
import com.fm.smartlearningplatform.course.service.CourseLearningOutcomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-learning-outcomes")
@RequiredArgsConstructor
public class CourseLearningOutcomeController {

    private final CourseLearningOutcomeService
            courseLearningOutcomeService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseLearningOutcomeResponse>
    createCourseLearningOutcome(
            @Valid
            @RequestBody
            CreateCourseLearningOutcomeRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseLearningOutcomeService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseLearningOutcomeResponse>
    getCourseLearningOutcomeById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseLearningOutcomeService.findById(id)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<CourseLearningOutcomeResponse>>
    getCourseLearningOutcomesByCourseId(
            @PathVariable Long courseId,

            @PageableDefault(
                    size = 10,
                    sort = "displayOrder"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                courseLearningOutcomeService.findByCourseId(
                        courseId,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseLearningOutcomeResponse>
    updateCourseLearningOutcomeById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseLearningOutcomeRequest request
    ) {

        return ResponseEntity.ok(
                courseLearningOutcomeService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseLearningOutcomeById(
            @PathVariable Long id
    ) {

        courseLearningOutcomeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}