package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.DeleteEducationLevelResponse;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.user.service.EducationLevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/educationLevels")
@RequiredArgsConstructor
public class EducationLevelController {

    private final EducationLevelService educationLevelService;

    @PostMapping
    public ResponseEntity<EducationLevelResponse> createEducationLevel(@Valid @RequestBody CreateEducationLevelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(educationLevelService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationLevelResponse> getEducationLevelById(@PathVariable Long id) {
        return ResponseEntity.ok(educationLevelService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EducationLevelResponse>> getEducationLevels(@RequestParam(value = "q", required = false) String keyword,
                                                                           @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(educationLevelService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationLevelResponse> updateEducationLevelById(@PathVariable Long id, @Valid @RequestBody UpdateEducationLevelRequest request) {
        return ResponseEntity.ok(educationLevelService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteEducationLevelResponse> deleteEducationLevelById(@PathVariable Long id) {
        return ResponseEntity.ok().body(educationLevelService.deleteById(id));
    }
}