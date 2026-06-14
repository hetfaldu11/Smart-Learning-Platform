package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseMedia.request.CreateCourseMediaRequest;
import com.fm.smartlearningplatform.course.dto.courseMedia.request.UpdateCourseMediaRequest;
import com.fm.smartlearningplatform.course.dto.courseMedia.response.CourseMediaResponse;
import com.fm.smartlearningplatform.course.service.CourseMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-media")
@RequiredArgsConstructor
public class CourseMediaController {

    private final CourseMediaService courseMediaService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseMediaResponse>
    createCourseMedia(
            @Valid
            @RequestBody
            CreateCourseMediaRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseMediaService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseMediaResponse>
    getCourseMediaByCourseId(
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                courseMediaService.findByCourseId(
                        courseId
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/course/{courseId}")
    public ResponseEntity<CourseMediaResponse>
    updateCourseMediaByCourseId(
            @PathVariable Long courseId,

            @Valid
            @RequestBody
            UpdateCourseMediaRequest request
    ) {

        return ResponseEntity.ok(
                courseMediaService.update(
                        courseId,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<Void>
    deleteCourseMediaByCourseId(
            @PathVariable Long courseId
    ) {

        courseMediaService.delete(courseId);

        return ResponseEntity.noContent().build();
    }
}