package com.fm.smartlearningplatform.section.controller;

import com.fm.smartlearningplatform.section.dto.sectionProgress.request.CreateSectionProgressRequest;
import com.fm.smartlearningplatform.section.dto.sectionProgress.request.UpdateSectionProgressRequest;
import com.fm.smartlearningplatform.section.dto.sectionProgress.response.SectionProgressResponse;
import com.fm.smartlearningplatform.section.service.SectionProgressService;
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
public class SectionProgressController {

    private final SectionProgressService sectionProgressService;

    // ─── Create ───────────────────────────────────────────────

//    @PostMapping("/section-progress")
//    public ResponseEntity<SectionProgressResponse> create(
//            @Valid @RequestBody CreateSectionProgressRequest request) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(sectionProgressService.create(request));
//    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/users/{userId}/sections/{sectionId}/progress")
    public ResponseEntity<SectionProgressResponse> findByUserAndSection(
            @PathVariable Long userId,
            @PathVariable Long sectionId) {
        return ResponseEntity.ok(sectionProgressService.findByUserAndSection(userId, sectionId));
    }

    // ─── Update ───────────────────────────────────────────────

//    @PatchMapping("/users/{userId}/sections/{sectionId}/progress")
//    public ResponseEntity<SectionProgressResponse> update(
//            @PathVariable Long userId,
//            @PathVariable Long sectionId,
//            @Valid @RequestBody UpdateSectionProgressRequest request) {
//        return ResponseEntity.ok(sectionProgressService.update(userId, sectionId, request));
//    }
}