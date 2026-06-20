package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.response.DeleteLanguageResponse;
import com.fm.smartlearningplatform.user.dto.language.response.LanguageResponse;
import com.fm.smartlearningplatform.user.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<LanguageResponse> createLanguage(@Valid @RequestBody CreateLanguageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(languageService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageResponse> getLanguageById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LanguageResponse>> getLanguages(
            @RequestParam(value = "q", required = false)
            String keyword,
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable
    ) {

        return ResponseEntity.ok(languageService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageResponse> updateLanguageById(@PathVariable Long id, @Valid @RequestBody UpdateLanguageRequest request) {
        return ResponseEntity.ok(languageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteLanguageResponse> deleteLanguageById(@PathVariable Long id) {
        return ResponseEntity.ok().body(languageService.deleteById(id));
    }
}