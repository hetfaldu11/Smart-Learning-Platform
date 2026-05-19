package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.service.user.ProfessionService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(professionService.createProfession(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionResponse> getProfession(@PathVariable Long id) {
        return ResponseEntity.ok(professionService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessionResponse>> getAllProfessions() {
        return ResponseEntity.ok(professionService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionResponse> updateProfession(@PathVariable Long id, @Valid @RequestBody UpdateProfessionRequest request) {
        return ResponseEntity.ok(professionService.updateProfession(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        professionService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}