package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseSupport.request.CreateCourseSupportRequest;
import com.fm.smartlearningplatform.course.dto.courseSupport.request.UpdateCourseSupportRequest;
import com.fm.smartlearningplatform.course.dto.courseSupport.response.CourseSupportResponse;
import com.fm.smartlearningplatform.course.service.CourseSupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-support")
@RequiredArgsConstructor
public class CourseSupportController {

    private final CourseSupportService
            courseSupportService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseSupportResponse>
    createCourseSupport(
            @Valid
            @RequestBody
            CreateCourseSupportRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseSupportService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseSupportResponse>
    getCourseSupportByCourseId(
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                courseSupportService.findByCourseId(
                        courseId
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseSupportResponse>
    updateCourseSupportByCourseId(
            @PathVariable Long courseId,

            @Valid
            @RequestBody
            UpdateCourseSupportRequest request
    ) {

        return ResponseEntity.ok(
                courseSupportService.update(
                        courseId,
                        request
                )
        );
    }




}