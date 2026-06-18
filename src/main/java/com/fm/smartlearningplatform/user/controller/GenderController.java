package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.response.DeleteGenderResponse;
import com.fm.smartlearningplatform.user.dto.gender.response.GenderResponse;
import com.fm.smartlearningplatform.user.service.GenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genders")
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderService;

    @PostMapping
    public ResponseEntity<GenderResponse> createGender(@Valid @RequestBody CreateGenderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genderService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderResponse> getGenderById(@PathVariable Long id) {
        return ResponseEntity.ok(genderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GenderResponse>> getGenders(@RequestParam(value = "q", required = false) String keyword,
                                                           @PageableDefault(size = 10, sort = "name") Pageable pageable ) {
        return ResponseEntity.ok(genderService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderResponse> updateGenderById(@PathVariable Long id, @Valid @RequestBody UpdateGenderRequest request) {
        return ResponseEntity.ok(genderService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteGenderResponse> deleteGenderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(genderService.deleteById(id));
    }
}