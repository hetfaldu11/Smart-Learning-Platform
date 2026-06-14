package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.coursePricing.request.CreateCoursePricingRequest;
import com.fm.smartlearningplatform.course.dto.coursePricing.request.UpdateCoursePricingRequest;
import com.fm.smartlearningplatform.course.dto.coursePricing.response.CoursePricingResponse;
import com.fm.smartlearningplatform.course.service.CoursePricingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-pricing")
@RequiredArgsConstructor
public class CoursePricingController {

    private final CoursePricingService
            coursePricingService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CoursePricingResponse>
    createCoursePricing(
            @Valid
            @RequestBody
            CreateCoursePricingRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        coursePricingService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CoursePricingResponse>
    getCoursePricingByCourseId(
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                coursePricingService.findByCourseId(
                        courseId
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/course/{courseId}")
    public ResponseEntity<CoursePricingResponse>
    updateCoursePricingByCourseId(
            @PathVariable Long courseId,

            @Valid
            @RequestBody
            UpdateCoursePricingRequest request
    ) {

        return ResponseEntity.ok(
                coursePricingService.update(
                        courseId,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<Void>
    deleteCoursePricingByCourseId(
            @PathVariable Long courseId
    ) {

        coursePricingService.delete(courseId);

        return ResponseEntity.noContent().build();
    }
}