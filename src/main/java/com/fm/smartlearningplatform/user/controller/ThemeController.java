package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.response.DeleteThemeResponse;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<ThemeResponse> createTheme(@Valid @RequestBody CreateThemeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(themeService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> getThemeById(@PathVariable Long id) {
        return ResponseEntity.ok(themeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ThemeResponse>> getThemes(@RequestParam(value = "q", required = false) String keyword,
                                                         @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(themeService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThemeResponse> updateThemeById(@PathVariable Long id, @Valid @RequestBody UpdateThemeRequest request) {
        return ResponseEntity.ok(themeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteThemeResponse> deleteThemeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(themeService.deleteById(id));
    }
}