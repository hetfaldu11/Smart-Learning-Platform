package com.fm.smartlearningplatform.section.controller;

import com.fm.smartlearningplatform.section.dto.section.request.CreateSectionRequest;
import com.fm.smartlearningplatform.section.dto.section.request.UpdateSectionRequest;
import com.fm.smartlearningplatform.section.dto.section.response.SectionResponse;
import com.fm.smartlearningplatform.section.service.SectionService;
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
public class SectionController {

    private final SectionService sectionService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping("/sections")
    public ResponseEntity<SectionResponse> create(@Valid @RequestBody CreateSectionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sectionService.create(request));
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/sections/{sectionId}")
    public ResponseEntity<SectionResponse> findById(@PathVariable Long sectionId) {
        return ResponseEntity.ok(sectionService.findById(sectionId));
    }

    @GetMapping("/courses/{courseId}/sections")
    public ResponseEntity<Page<SectionResponse>> findAllByCourse(
            @PathVariable Long courseId,
            @PageableDefault(size = 10, sort = "position") Pageable pageable) {
        return ResponseEntity.ok(sectionService.findAllByCourse(courseId, pageable));
    }

    // ─── Update ───────────────────────────────────────────────

    @PatchMapping("/sections/{sectionId}")
    public ResponseEntity<SectionResponse> update(
            @PathVariable Long sectionId,
            @Valid @RequestBody UpdateSectionRequest request) {
        return ResponseEntity.ok(sectionService.update(sectionId, request));
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<Void> delete(@PathVariable Long sectionId) {
        sectionService.delete(sectionId);
        return ResponseEntity.noContent().build();
    }
}