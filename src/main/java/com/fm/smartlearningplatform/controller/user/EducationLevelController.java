package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.model.user.EducationLevel;
import com.fm.smartlearningplatform.service.user.EducationLevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(educationLevelService.createEducationLevel(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationLevelResponse> getEducationLevel(@PathVariable Long id) {
        return ResponseEntity.ok(educationLevelService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<EducationLevelResponse>> getAllEducationLevels() {
        return ResponseEntity.ok(educationLevelService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationLevelResponse> updateEducationLevel(@PathVariable Long id, @Valid @RequestBody UpdateEducationLevelRequest request) {
        return ResponseEntity.ok(educationLevelService.updateEducationLevel(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducationLevel(@PathVariable Long id) {
        educationLevelService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}