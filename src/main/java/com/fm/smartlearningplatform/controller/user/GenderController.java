package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.response.GenderResponse;
import com.fm.smartlearningplatform.model.user.Gender;
import com.fm.smartlearningplatform.service.user.GenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genders")
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderService;

    @PostMapping
    public ResponseEntity<GenderResponse> createGender(@Valid @RequestBody CreateGenderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genderService.createGender(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderResponse> getGender(@PathVariable Long id) {
        return ResponseEntity.ok(genderService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<GenderResponse>> getAllGenders() {
        return ResponseEntity.ok(genderService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderResponse> updateGender(@PathVariable Long id, @Valid @RequestBody UpdateGenderRequest request) {
        return ResponseEntity.ok(genderService.updateGender(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGender(@PathVariable Long id) {
        genderService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}