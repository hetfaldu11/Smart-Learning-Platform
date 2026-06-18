package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseRequirement.request.CreateCourseRequirementRequest;
import com.fm.smartlearningplatform.course.dto.courseRequirement.request.UpdateCourseRequirementRequest;
import com.fm.smartlearningplatform.course.dto.courseRequirement.response.CourseRequirementResponse;
import com.fm.smartlearningplatform.course.service.CourseRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-requirements")
@RequiredArgsConstructor
public class CourseRequirementController {

    private final CourseRequirementService
            courseRequirementService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseRequirementResponse>
    createCourseRequirement(
            @Valid
            @RequestBody
            CreateCourseRequirementRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseRequirementService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseRequirementResponse>
    getCourseRequirementById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseRequirementService.findById(id)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<CourseRequirementResponse>>
    getCourseRequirementsByCourseId(
            @PathVariable Long courseId,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                courseRequirementService.findByCourseId(
                        courseId,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseRequirementResponse>
    updateCourseRequirementById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseRequirementRequest request
    ) {

        return ResponseEntity.ok(
                courseRequirementService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseRequirementById(
            @PathVariable Long id
    ) {

        courseRequirementService.delete(id);

        return ResponseEntity.noContent().build();
    }
}