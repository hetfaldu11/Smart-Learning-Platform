package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseMessageType.request.CreateCourseMessageTypeRequest;
import com.fm.smartlearningplatform.course.dto.courseMessageType.request.UpdateCourseMessageTypeRequest;
import com.fm.smartlearningplatform.course.dto.courseMessageType.response.CourseMessageTypeResponse;
import com.fm.smartlearningplatform.course.service.CourseMessageTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-message-types")
@RequiredArgsConstructor
public class CourseMessageTypeController {

    private final CourseMessageTypeService
            courseMessageTypeService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseMessageTypeResponse>
    createCourseMessageType(
            @Valid
            @RequestBody
            CreateCourseMessageTypeRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseMessageTypeService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseMessageTypeResponse>
    getCourseMessageTypeById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseMessageTypeService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<CourseMessageTypeResponse>>
    getCourseMessageTypes(
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
                courseMessageTypeService.search(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseMessageTypeResponse>
    updateCourseMessageTypeById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseMessageTypeRequest request
    ) {

        return ResponseEntity.ok(
                courseMessageTypeService.update(
                        id,
                        request
                )
        );
    }
}