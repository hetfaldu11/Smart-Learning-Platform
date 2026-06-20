package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.request.CreateCourseAssistantInstructorRequest;
import com.fm.smartlearningplatform.course.dto.courseAssistantInstructor.response.CourseAssistantInstructorResponse;
import com.fm.smartlearningplatform.course.service.CourseAssistantInstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-assistant-instructors")
@RequiredArgsConstructor
public class CourseAssistantInstructorController {

    private final CourseAssistantInstructorService
            courseAssistantInstructorService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseAssistantInstructorResponse>
    createCourseAssistantInstructor(
            @Valid
            @RequestBody
            CreateCourseAssistantInstructorRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseAssistantInstructorService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CourseAssistantInstructorResponse>
    getCourseAssistantInstructorById(@PathVariable Long id)
    {
        return ResponseEntity.ok(courseAssistantInstructorService.findById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<CourseAssistantInstructorResponse>>
    getCourseAssistantInstructorsByCourseId(
            @PathVariable Long courseId,
            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                courseAssistantInstructorService.findByCourseId(
                        courseId,
                        pageable
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCourseAssistantInstructorById(
            @PathVariable Long id
    )
    {
        courseAssistantInstructorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}