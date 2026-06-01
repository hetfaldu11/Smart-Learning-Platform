package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.response.DeleteLanguageResponse;
import com.fm.smartlearningplatform.user.dto.language.response.LanguageResponse;
import com.fm.smartlearningplatform.user.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<LanguageResponse>> getLanguages(@RequestParam(value = "q", required = false) String keyword) {
        return ResponseEntity.ok(languageService.searchByKeyword(keyword));
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