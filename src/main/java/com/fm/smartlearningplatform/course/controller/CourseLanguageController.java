package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseLanguage.request.CreateCourseLanguageRequest;
import com.fm.smartlearningplatform.course.dto.courseLanguage.response.CourseLanguageResponse;
import com.fm.smartlearningplatform.course.service.CourseLanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-languages")
@RequiredArgsConstructor
public class CourseLanguageController {

    private final CourseLanguageService
            courseLanguageService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseLanguageResponse>
    createCourseLanguage(
            @Valid
            @RequestBody
            CreateCourseLanguageRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseLanguageService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseLanguageResponse>
    getCourseLanguageById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseLanguageService.findById(id)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<CourseLanguageResponse>>
    getCourseLanguagesByCourseId(
            @PathVariable Long courseId,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                courseLanguageService.findByCourseId(
                        courseId,
                        pageable
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseLanguageById(
            @PathVariable Long id
    ) {

        courseLanguageService.delete(id);

        return ResponseEntity.noContent().build();
    }
}