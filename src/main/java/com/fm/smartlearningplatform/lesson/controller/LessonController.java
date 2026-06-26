package com.fm.smartlearningplatform.lesson.controller;

import com.fm.smartlearningplatform.lesson.dto.lesson.request.CreateLessonRequest;
import com.fm.smartlearningplatform.lesson.dto.lesson.request.UpdateLessonRequest;
import com.fm.smartlearningplatform.lesson.dto.lesson.response.LessonResponse;
import com.fm.smartlearningplatform.lesson.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping("/lessons")
    public ResponseEntity<LessonResponse> create(
            @Valid @RequestBody CreateLessonRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonService.create(request));
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonResponse> findById(
            @PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.findById(lessonId));
    }

    @GetMapping("/sections/{sectionId}/lessons")
    public ResponseEntity<Page<LessonResponse>> findBySectionId(
            @PathVariable Long sectionId,
            @PageableDefault(size = 10, sort = "position") Pageable pageable) {
        return ResponseEntity.ok(lessonService.findBySectionId(sectionId, pageable));
    }

    // ─── Update ───────────────────────────────────────────────

    @PatchMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonResponse> update(
            @PathVariable Long lessonId,
            @Valid @RequestBody UpdateLessonRequest request) {
        return ResponseEntity.ok(lessonService.update(lessonId, request));
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long lessonId) {
        lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }
}