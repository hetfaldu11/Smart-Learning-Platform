package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.response.LanguageResponse;
import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.service.user.LanguageService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(languageService.createLanguage(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageResponse> getLanguage(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getAllLanguages() {
        return ResponseEntity.ok(languageService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageResponse> updateLanguage(@PathVariable Long id, @Valid @RequestBody UpdateLanguageRequest request) {
        return ResponseEntity.ok(languageService.updateLanguage(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        languageService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}