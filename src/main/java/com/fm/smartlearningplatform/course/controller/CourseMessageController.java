package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseMessage.request.CreateCourseMessageRequest;
import com.fm.smartlearningplatform.course.dto.courseMessage.request.UpdateCourseMessageRequest;
import com.fm.smartlearningplatform.course.dto.courseMessage.response.CourseMessageResponse;
import com.fm.smartlearningplatform.course.service.CourseMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-messages")
@RequiredArgsConstructor
public class CourseMessageController {

    private final CourseMessageService courseMessageService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseMessageResponse>
    createCourseMessage(
            @Valid
            @RequestBody
            CreateCourseMessageRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseMessageService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseMessageResponse>
    getCourseMessageById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                courseMessageService.findById(id)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<CourseMessageResponse>>
    getCourseMessagesByCourseId(
            @PathVariable Long courseId,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                courseMessageService.findByCourseId(
                        courseId,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CourseMessageResponse>
    updateCourseMessageById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCourseMessageRequest request
    ) {

        return ResponseEntity.ok(
                courseMessageService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseMessageById(
            @PathVariable Long id
    ) {

        courseMessageService.delete(id);

        return ResponseEntity.noContent().build();
    }
}