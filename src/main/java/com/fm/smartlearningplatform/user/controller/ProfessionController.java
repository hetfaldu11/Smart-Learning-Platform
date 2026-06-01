package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.response.DeleteProfessionResponse;
import com.fm.smartlearningplatform.user.dto.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.user.service.ProfessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professions")
@RequiredArgsConstructor
public class ProfessionController {

    private final ProfessionService professionService;

    @PostMapping
    public ResponseEntity<ProfessionResponse> createProfession(@Valid @RequestBody CreateProfessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professionService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionResponse> getProfessionById(@PathVariable Long id) {
        return ResponseEntity.ok(professionService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessionResponse>> getProfessions(@RequestParam(value = "q", required = false) String keyword) {
        return ResponseEntity.ok(professionService.searchByKeyword(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionResponse> updateProfessionById(@PathVariable Long id, @Valid @RequestBody UpdateProfessionRequest request) {
        return ResponseEntity.ok(professionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProfessionResponse> deleteProfessionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(professionService.deleteById(id));
    }
}