package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<ThemeResponse> createTheme(@Valid @RequestBody CreateThemeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(themeService.createTheme(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> getTheme(@PathVariable Long id) {
        return ResponseEntity.ok(themeService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getAllThemes() {
        return ResponseEntity.ok(themeService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThemeResponse> updateTheme(@PathVariable Long id, @Valid @RequestBody UpdateThemeRequest request) {
        return ResponseEntity.ok(themeService.updateTheme(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}